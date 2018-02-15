CREATE TABLE `capture_trace` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` bigint(20) NOT NULL,
  `direction` int(11) DEFAULT NULL,
  `end_trigger_time` bigint(20) DEFAULT NULL,
  `sample_count` int(11) NOT NULL,
  `start_trigger_time` bigint(20) DEFAULT NULL,
  `time_delta` double NOT NULL,
  `comments` longtext,
  `tag` varchar(25) NOT NULL DEFAULT 'NORMAL',
  `min_sample` double DEFAULT NULL,
  `max_sample` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPRESSED;

CREATE TABLE `capture_trace_sample` (
  `capture_trace` bigint(20) NOT NULL,
  `sample_number` smallint(5) unsigned NOT NULL,
  `value` double NOT NULL,
  PRIMARY KEY (`capture_trace`,`sample_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPRESSED KEY_BLOCK_SIZE=4
/*!50100 PARTITION BY HASH (capture_trace)
PARTITIONS 100 */;