package org.beckn.one.sandbox.bap.client.order.update.mappers

import arrow.core.Either
import org.beckn.one.sandbox.bap.client.shared.dtos.ClientCancelResponse
import org.beckn.one.sandbox.bap.client.shared.services.GenericOnPollMapper
import org.beckn.one.sandbox.bap.errors.HttpError
import org.beckn.protocol.schemas.ProtocolContext
import org.beckn.protocol.schemas.ProtocolOnCancel

class UpdateClientResponseMapper : GenericOnPollMapper<ProtocolOnUpdate, ClientCancelResponse> {
  override fun transform(
    input: List<ProtocolOnUpdate>,
    context: ProtocolContext
  ): Either<HttpError, ClientCancelResponse> =
    Either.Right(
      ClientCancelResponse(
        context = context,
        message = input.firstOrNull()?.message,
        error = input.firstOrNull()?.error
      )
    )
}