package org.beckn.one.sandbox.bap.protocol.rating.controllers

import org.beckn.one.sandbox.bap.message.services.ResponseStorageService
import org.beckn.one.sandbox.bap.protocol.shared.controllers.AbstractCallbackController
import org.beckn.one.sandbox.bap.protocol.shared.services.LoggingService
import org.beckn.one.sandbox.bap.schemas.factories.LoggingFactory
import org.beckn.protocol.schemas.ProtocolContext
import org.beckn.protocol.schemas.ProtocolOnRating
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class OnRatingController  @Autowired constructor(
  store: ResponseStorageService<ProtocolOnRating>,
  loggingFactory: LoggingFactory,
  loggingService: LoggingService
) :
  AbstractCallbackController<ProtocolOnRating>(store, loggingFactory, loggingService) {

  @PostMapping(
    "protocol/v1/on_rating",
    consumes = [MediaType.APPLICATION_JSON_VALUE],
    produces = [MediaType.APPLICATION_JSON_VALUE],
  )
  fun onRating(@RequestBody ratingResponse: ProtocolOnRating) = onCallback(
    ratingResponse,
    ProtocolContext.Action.ON_RATING
  )

}