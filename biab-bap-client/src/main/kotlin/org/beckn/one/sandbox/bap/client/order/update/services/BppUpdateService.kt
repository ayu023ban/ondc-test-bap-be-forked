package org.beckn.one.sandbox.bap.client.order.cancel.services

import arrow.core.Either
import org.beckn.one.sandbox.bap.client.external.hasBody
import org.beckn.one.sandbox.bap.client.external.isAckNegative
import org.beckn.one.sandbox.bap.client.external.isInternalServerError
import org.beckn.one.sandbox.bap.client.external.provider.BppClientFactory
import org.beckn.one.sandbox.bap.client.shared.errors.bpp.BppError
import org.beckn.protocol.schemas.ProtocolAckResponse
import org.beckn.protocol.schemas.ProtocolCancelRequest
import org.beckn.protocol.schemas.ProtocolCancelRequestMessage
import org.beckn.protocol.schemas.ProtocolContext
import org.beckn.protocol.schemas.ProtocolOrder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BppUpdateService @Autowired constructor(
  private val bppServiceClientFactory: BppClientFactory
) {
  private val log: Logger = LoggerFactory.getLogger(BppUpdateService::class.java)

  fun updateOrder(
    bppUri: String,
    context: ProtocolContext,
    updateType: String,
    order: ProtocolOrder,
  ): Either<BppError, ProtocolAckResponse> =
    Either.catch {
      log.info("Invoking update order API on BPP: {}", bppUri)
      val bppServiceClient = bppServiceClientFactory.getClient(bppUri)
      val httpResponse = bppServiceClient.update(
        ProtocolUpdateRequest(
          context = context,
          message = ProtocolUpdateRequestMessage(
            update_type = updateType,
            order = order
          )
        )
      ).execute()
      log.info("BPP update order response. Status: {}, Body: {}", httpResponse.code(), httpResponse.body())
      return when {
        httpResponse.isInternalServerError() -> Either.Left(BppError.Internal)
        !httpResponse.hasBody() -> Either.Left(BppError.NullResponse)
        httpResponse.isAckNegative() -> Either.Left(BppError.Nack)
        else -> Either.Right(httpResponse.body()!!)
      }
    }.mapLeft {
      log.error("Error when invoking BPP update API", it)
      BppError.Internal
    }
}