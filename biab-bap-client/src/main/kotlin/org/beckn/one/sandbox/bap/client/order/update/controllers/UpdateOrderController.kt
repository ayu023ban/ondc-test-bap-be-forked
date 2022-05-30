package org.beckn.one.sandbox.bap.client.order.update.controllers

import org.beckn.one.sandbox.bap.client.order.cancel.services.CancelOrderService
import org.beckn.one.sandbox.bap.client.shared.dtos.CancelOrderDto
import org.beckn.one.sandbox.bap.client.shared.services.LoggingService
import org.beckn.one.sandbox.bap.errors.HttpError
import org.beckn.one.sandbox.bap.factories.ContextFactory
import org.beckn.one.sandbox.bap.factories.LoggingFactory
import org.beckn.protocol.schemas.ProtocolAckResponse
import org.beckn.protocol.schemas.ProtocolContext
import org.beckn.protocol.schemas.ResponseMessage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UpdateOrderController @Autowired constructor(
  private val contextFactory: ContextFactory,
  private val cancelOrderService: CancelOrderService,
  private val loggingFactory: LoggingFactory,
  private val loggingService: LoggingService
) {
  val log: Logger = LoggerFactory.getLogger(this::class.java)

  @PostMapping("/client/v1/update_order")
  @ResponseBody
  fun updateOrderV1(@RequestBody request: CancelOrderDto): ResponseEntity<ProtocolAckResponse> {
    log.info("Got request to update order")
    val context = getContext(request.context.transactionId, request.context.bppId)
    setLogging(context, null)
    return cancelOrderService.cancel(
      context = context,
      orderId = request.message.orderId,
      cancellationReasonId = request.message.cancellationReasonId
    ).fold(
      {
        log.error("Error when updating order with BPP: {}", it)
        setLogging(context, it)
        mapToErrorResponse(it, context)
      },
      {
        log.info("Successfully updated order with BPP. Message: {}", it)
        setLogging(context, null)
        ResponseEntity.ok(ProtocolAckResponse(context = context, message = ResponseMessage.ack()))
      }
    )
  }

  private fun mapToErrorResponse(it: HttpError, context: ProtocolContext) = ResponseEntity
    .status(it.status())
    .body(
      ProtocolAckResponse(
        context = context,
        message = it.message(),
        error = it.error()
      )
    )

  private fun setLogging(context: ProtocolContext, error: HttpError?) {
    val loggerRequest = loggingFactory.create(messageId = context.messageId,
      transactionId = context.transactionId, contextTimestamp = context.timestamp.toString(),
      action = ProtocolContext.Action.UPDATE, bppId = context.bppId, errorCode = error?.error()?.code,
      errorMessage = error?.error()?.message
    )
    loggingService.postLog(loggerRequest)
  }

  private fun getContext(transactionId: String, bppId: String? = null) =
    contextFactory.create(action = ProtocolContext.Action.UPDATE, transactionId = transactionId, bppId = bppId)
}