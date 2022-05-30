package org.beckn.protocol.schemas

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import kotlinx.serialization.Serializable

interface ProtocolResponse {
  val context: ProtocolContext?
  val error: ProtocolError?
}

data class ProtocolErrorResponse(
  override val context: ProtocolContext,
  override val error: ProtocolError? = null
) : ProtocolResponse

data class ResponseMessage @Default constructor(val ack: ProtocolAck) {
  companion object {
    fun ack(): ResponseMessage = ResponseMessage(ProtocolAck(ResponseStatus.ACK))
    fun nack(): ResponseMessage = ResponseMessage(ProtocolAck(ResponseStatus.NACK))
  }
}

@Serializable
enum class ResponseStatus(
  val status: String
) {
  ACK("ACK"),
  NACK("NACK");
}

data class ProtocolAckResponse(
  override val context: ProtocolContext?,
  val message: ResponseMessage,
  override val error: ProtocolError? = null,
) : ProtocolResponse

@JsonIgnoreProperties(ignoreUnknown = true)
data class ProtocolOnSearch @Default constructor(
  override val context: ProtocolContext? = null,
  val message: ProtocolOnSearchMessage?,
  override val error: ProtocolError? = null,
) : ProtocolResponse

data class ProtocolOnSearchMessage @Default constructor(
  val catalog: ProtocolCatalog? = null
)

data class ProtocolOnSelect @Default constructor(
  override val context: ProtocolContext? = null,
  val message: ProtocolOnSelectMessage?,
  override val error: ProtocolError? = null
) : ProtocolResponse

data class ProtocolOnSelectMessage @Default constructor(
  val order: ProtocolOnSelectMessageSelected? = null
)


data class ProtocolOnInit @Default constructor(
  override val context: ProtocolContext? = null,
  val message: ProtocolOnInitMessage?,
  override val error: ProtocolError? = null
) : ProtocolResponse

data class ProtocolOnInitMessage @Default constructor(
  val order: ProtocolOnInitMessageInitialized? = null
)

data class ProtocolOnConfirm @Default constructor(
  override val context: ProtocolContext? = null,
  val message: ProtocolOnConfirmMessage,
  override val error: ProtocolError? = null
) : ProtocolResponse

data class ProtocolOnConfirmMessage @Default constructor(
  val order: ProtocolOrder? = null
)

data class ProtocolOnTrack @Default constructor(
  override val context: ProtocolContext? = null,
  val message: ProtocolOnTrackMessage,
  override val error: ProtocolError? = null
) : ProtocolResponse

data class ProtocolOnTrackMessage @Default constructor(
  val tracking: ProtocolOnTrackMessageTracking? = null
)

data class ProtocolOnSupport @Default constructor(
  override val context: ProtocolContext? = null,
  val message: ProtocolOnSupportMessage,
  override val error: ProtocolError? = null
) : ProtocolResponse

data class ProtocolOnSupportMessage @Default constructor(
  val phone: String? = null,
  val email: String? = null,
  val uri: String? = null
)

data class ProtocolOnRating @Default constructor(
  override val context: ProtocolContext? = null,
  val message: ProtocolOnRatingMessage?,
  override val error: ProtocolError? = null
) : ProtocolResponse

data class ProtocolOnRatingMessage @Default constructor(
  val feedback: ProtocolOnRatingMessageFeedback? = null
)

data class ProtocolOnOrderStatus @Default constructor(
  override val context: ProtocolContext? = null,
  val message: ProtocolOnOrderStatusMessage?,
  override val error: ProtocolError? = null
) : ProtocolResponse

data class ProtocolOnOrderStatusMessage @Default constructor(
  val order: ProtocolOrder? = null
)

data class ProtocolOnCancel @Default constructor(
  override val context: ProtocolContext? = null,
  val message: ProtocolOnCancelMessage?,
  override val error: ProtocolError? = null
) : ProtocolResponse

data class ProtocolOnCancelMessage @Default constructor(
  val order: ProtocolOrder
)

data class ProtocolOnUpdate @Default constructor(
  override val context: ProtocolContext? = null,
  val message: ProtocolOnUpdateMessage?,
  override val error: ProtocolError? = null
) : ProtocolResponse

data class ProtocolOnUpdateMessage @Default constructor(
  val order: ProtocolOrder
)

data class ProtocolRatingCategoriesResponse @Default constructor(
  val Category: ProtocolCategory
)

data class ProtocolOnCancellationReasons @Default constructor(
  override val context: ProtocolContext? = null,
  val message: ProtocolOnCancellationReasonMessage?,
  override val error: ProtocolError? = null
) : ProtocolResponse

data class ProtocolOnCancellationReasonMessage @Default constructor(
  val cancellationReasons: List<ProtocolOption>? = null,
  val ratingCategories: List<ProtocolRatingCategory>? = null
)

