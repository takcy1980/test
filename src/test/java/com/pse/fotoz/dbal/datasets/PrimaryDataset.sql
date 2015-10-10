-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Oct 10, 2015 at 05:57 PM
-- Server version: 5.6.24
-- PHP Version: 5.6.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `fotoz`
--
DROP DATABASE IF EXISTS `fotoz`;
CREATE DATABASE IF NOT EXISTS `fotoz` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `fotoz`;

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE IF NOT EXISTS `customers` (
  `id` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `customer_accounts`
--

CREATE TABLE IF NOT EXISTS `customer_accounts` (
  `id` int(11) NOT NULL,
  `login` varchar(255) DEFAULT NULL,
  `passwordHash` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `photographers`
--

CREATE TABLE IF NOT EXISTS `photographers` (
  `id` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `photographers`
--

INSERT INTO `photographers` (`id`, `address`, `city`, `email`, `name`, `phone`) VALUES
(1, 'Hazelpad 23', 'Eindhoven', 'info@avonturier.nl', 'De Avonturier', '0401234567'),
(3, 'Schoenmaker 187', 'Eindhoven', 'info@kolibrie.nl', 'Kolibrie Natuurfoto''s', '0401234567'),
(5, 'De Vaar 18', 'Eindhoven', 'info@selectfotografie.nl', 'Select Fotografie', '0401234567'),
(7, 'Molenweg 4', 'Eindhoven', 'info@mooiekiekjes.nl', 'Mooie Kiekjes Eindhoven', '0401234567'),
(9, 'Steenstraat 58', 'Eindhoven', 'info@willemderijk.nl', 'Willem de Rijk Fotografie', '0401234567'),
(11, 'Geldropseweg 186', 'Eindhoven', 'info@hansgroen.nl', 'Hans Groen Fotografie', '0401234567');

-- --------------------------------------------------------

--
-- Table structure for table `pictures`
--

CREATE TABLE IF NOT EXISTS `pictures` (
  `id` int(11) NOT NULL,
  `approved` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `filename` varchar(255) DEFAULT NULL,
  `height` int(11) DEFAULT NULL,
  `hidden` bit(1) DEFAULT NULL,
  `price` decimal(19,2) DEFAULT NULL,
  `submission_date` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `width` int(11) DEFAULT NULL,
  `picture_session_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pictures`
--

INSERT INTO `pictures` (`id`, `approved`, `description`, `filename`, `height`, `hidden`, `price`, `submission_date`, `title`, `width`, `picture_session_id`) VALUES
(0, 'YES', NULL, '582014.jpg', NULL, b'0', NULL, '2015-10-09 00:00:00', NULL, NULL, 4),
(1, 'YES', NULL, '582752.jpg', NULL, b'0', NULL, '2015-10-09 00:00:00', NULL, NULL, 4),
(2, 'YES', NULL, '58714.jpg', NULL, b'0', NULL, '2015-10-09 00:00:00', NULL, NULL, 1),
(3, 'YES', NULL, '58794.jpg', NULL, b'0', NULL, '2015-10-09 00:00:00', NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `picture_sessions`
--

CREATE TABLE IF NOT EXISTS `picture_sessions` (
  `id` int(11) NOT NULL,
  `code` varchar(255) NOT NULL,
  `shop_id` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `public` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `picture_sessions`
--

INSERT INTO `picture_sessions` (`id`, `code`, `shop_id`, `description`, `title`, `public`) VALUES
(0, 'uitstapje-bieschbos#9572', 2, 'Ondanks het niet al te mooie weer hebben we het voor elkaar gekregen om toch mooie plaatjes van de fauna in het Bieschbos te schieten.', 'Uitstapje Bieschbos in maart.', b'1'),
(1, 'ardennen-maart-2015#6832', 2, 'Mooie plaatjes uit de Ardennen van de eerste week in maart.', 'Ardennen maart 2015', b'1'),
(2, 'herfstvogels-2015#7201', 4, 'Vogels in de herfst van dit jaar.', 'Herfstvogels 2015', b'1'),
(3, 'fam_zengers_962#0693', 6, 'Familiefoto fam. Zengers', 'Familiefoto fam. Zengers', b'0'),
(4, 'blft-8-2-2015#0538', 8, 'Bruiloft Jannie en Mark', 'Bruiloft Jannie en Mark', b'0'),
(5, 'intocht-sinterklaas#9472', 10, 'Intocht Sinterklaas 2015', 'Intocht Sinterklaas 2015', b'1'),
(6, 'cultentumult2015#5901', 12, 'Cult en Tumult Veldhoven 2015', 'Cult en Tumult Veldhoven 2015', b'1');

-- --------------------------------------------------------

--
-- Table structure for table `producer_accounts`
--

CREATE TABLE IF NOT EXISTS `producer_accounts` (
  `id` int(11) NOT NULL,
  `login` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `producer_accounts`
--

INSERT INTO `producer_accounts` (`id`, `login`, `password`, `role`) VALUES
(0, 'admin', '1000:69a414cea0f36acc562686eb54d696f25c34a4ea1a519fa9:8945f0cbb0cac0853767cfd498d290df6027d24612059ba5', 'ROLE_ADMIN');

-- --------------------------------------------------------

--
-- Table structure for table `shops`
--

CREATE TABLE IF NOT EXISTS `shops` (
  `id` int(11) NOT NULL,
  `login` varchar(255) DEFAULT NULL,
  `passwordHash` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `shops`
--

INSERT INTO `shops` (`id`, `login`, `passwordHash`, `user_id`) VALUES
(2, 'de-avonturier', '1000:cbc3699dac8b9a73d6e7490c834c98a79f9c610fd9e7e0c0:1838333f3f46d8678c97134568ce08388b88850d247171d9', 1),
(4, 'de-kolibrie', '1000:bfde86f3afac1d8a0d7219da40d9627cdf5410906529cb15:92efa43069bf585efc413825476a6d1408409dfc2ba4d7ad', 3),
(6, 'select-fotografie', '1000:23038ed1f6cfddc6100123f095741591fdb15b24e24e670f:85b6754190982145b8c74beaac2281c55b3387beccd50b97', 5),
(8, 'mooie-kiekjes', '1000:c9277e12463311a1d08cf156406f4e413e2e0c9e09e95af6:b01b17b6659be60fffdd27a59023a2f8c5497636170da7a6', 7),
(10, 'willem-derijk', '1000:dbd10ae743009ae304bfd04596e18799ffc7fbe414eeaab4:cb17814333275c0d1a00bc65458a2361a43458316d854c8e', 9),
(12, 'hans-groen', '1000:ff7764fa7abeffe2acb3b17df0c5dd2637610006b6fca588:bcd9640d93aa81ff414de538f16ba5c0b9909190d5d89b05', 11);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `customer_accounts`
--
ALTER TABLE `customer_accounts`
  ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `UK_aq5uf5nbsbn7tq5eihspgjhm2` (`login`), ADD KEY `FKisiq6hhh572pshto31i0mur69` (`user_id`);

--
-- Indexes for table `photographers`
--
ALTER TABLE `photographers`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pictures`
--
ALTER TABLE `pictures`
  ADD PRIMARY KEY (`id`), ADD KEY `FK2auidntdcnjtijf1o2s26yo3r` (`picture_session_id`);

--
-- Indexes for table `picture_sessions`
--
ALTER TABLE `picture_sessions`
  ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `UK_svtat3pq2lfpndq558l5ds6qr` (`code`), ADD KEY `FKsb64d8agn9p4r7evmolqkgb3s` (`shop_id`);

--
-- Indexes for table `producer_accounts`
--
ALTER TABLE `producer_accounts`
  ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `UK_mmkr2cx7lc8ck4chqau0texy7` (`login`);

--
-- Indexes for table `shops`
--
ALTER TABLE `shops`
  ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `UK_tj1rl4ohchf9d7brpplitbt85` (`login`), ADD KEY `FKrw8rhg1ie517705qg85hd49x9` (`user_id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `customer_accounts`
--
ALTER TABLE `customer_accounts`
ADD CONSTRAINT `FKisiq6hhh572pshto31i0mur69` FOREIGN KEY (`user_id`) REFERENCES `customers` (`id`);

--
-- Constraints for table `pictures`
--
ALTER TABLE `pictures`
ADD CONSTRAINT `FK2auidntdcnjtijf1o2s26yo3r` FOREIGN KEY (`picture_session_id`) REFERENCES `picture_sessions` (`id`);

--
-- Constraints for table `picture_sessions`
--
ALTER TABLE `picture_sessions`
ADD CONSTRAINT `FKsb64d8agn9p4r7evmolqkgb3s` FOREIGN KEY (`shop_id`) REFERENCES `shops` (`id`);

--
-- Constraints for table `shops`
--
ALTER TABLE `shops`
ADD CONSTRAINT `FKrw8rhg1ie517705qg85hd49x9` FOREIGN KEY (`user_id`) REFERENCES `photographers` (`id`);
