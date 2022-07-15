package com.miumiuhaskeer.fastmessage.statistic.exception

import com.miumiuhaskeer.fastmessage.statistic.properties.bundle.ErrorBundle

/**
 * Throws if some field in Kafka value not valid
 *
 * @see com.miumiuhaskeer.fastmessage.statistic.validator.KafkaRecordValidator
 */
class KafkaValueNotValidException: RuntimeException(ErrorBundle.get("error.kafkaValueNotValidException.message"))