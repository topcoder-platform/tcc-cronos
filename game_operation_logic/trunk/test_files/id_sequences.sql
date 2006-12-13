# MySQL-Front 3.2  (Build 1.20)

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET CHARACTER SET 'gbk' */;


# Host: 192.168.1.159    Database: test
# ------------------------------------------------------
# Server version 4.1.18-standard-log

#
# Table structure for table id_sequences
#

CREATE TABLE `id_sequences` (
  `name` varchar(255) NOT NULL default '',
  `next_block_start` bigint(20) NOT NULL default '0',
  `block_size` int(11) NOT NULL default '0',
  `exhausted` tinyint(4) default '0',
  PRIMARY KEY  (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table id_sequences
#

INSERT INTO `id_sequences` VALUES ('UserProfile',15,5,0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
