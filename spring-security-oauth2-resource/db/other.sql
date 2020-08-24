CREATE TABLE `security_config` (
  `id` bigint(20) NOT NULL,
  `pattern` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

