package org.beckn.protocol.schemas

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Clock
import java.time.OffsetDateTime

data class ProtocolContext @Default constructor(
  val domain: String,
  val country: String,
  val city: String,
  val action: Action?,
  val coreVersion: String,
  val bapId: String? = null,
  val bapUri: String? = null,
  val bppId: String? = null,
  val bppUri: String? = null,
  val transactionId: String = "d4d65ff8-0d60-49bf-9288-a07261bb3f29",
  val messageId: String = "d883b720-2100-4a4e-a779-8685cd501e94",
  @JsonIgnore val clock: Clock = Clock.systemUTC(),
  val timestamp: OffsetDateTime = OffsetDateTime.now(clock),
  val key: String? = null,
  val ttl: String? = null,
) {
  enum class Action(val value: String) {
    @JsonProperty("search")
    SEARCH("search"),
    @JsonProperty("select")
    SELECT("select"),
    @JsonProperty("init")
    INIT("`init`"),
    @JsonProperty("confirm")
    CONFIRM("confirm"),
    @JsonProperty("update")
    UPDATE("update"),
    @JsonProperty("status")
    STATUS("status"),
    @JsonProperty("track")
    TRACK("track"),
    @JsonProperty("cancel")
    CANCEL("cancel"),
    @JsonProperty("rating")
    RATING("rating"),
    @JsonProperty("support")
    SUPPORT("support"),
    @JsonProperty("update")
    UPDATE("update"),
    @JsonProperty("on_search")
    ON_SEARCH("on_search"),
    @JsonProperty("on_select")
    ON_SELECT("on_select"),
    @JsonProperty("on_init")
    ON_INIT("on_init"),
    @JsonProperty("on_confirm")
    ON_CONFIRM("on_confirm"),
    @JsonProperty("on_update")
    ON_UPDATE("on_update"),
    @JsonProperty("on_status")
    ON_STATUS("on_status"),
    @JsonProperty("on_track")
    ON_TRACK("on_track"),
    @JsonProperty("on_cancel")
    ON_CANCEL("on_cancel"),
    @JsonProperty("on_rating")
    ON_RATING("on_rating"),
    @JsonProperty("on_support")
    ON_SUPPORT("on_support"),
    @JsonProperty("on_update")
    ON_UPDATE("on_update")
  }
}