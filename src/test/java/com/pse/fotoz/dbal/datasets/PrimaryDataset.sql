-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Oct 10, 2015 at 07:06 PM
-- Server version: 5.6.24
-- PHP Version: 5.6.8

SET FOREIGN_KEY_CHECKS=0;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `fotoz`
--

--
-- Truncate table before insert `customers`
--

TRUNCATE TABLE `customers`;
--
-- Truncate table before insert `customer_accounts`
--

TRUNCATE TABLE `customer_accounts`;
--
-- Truncate table before insert `photographers`
--

TRUNCATE TABLE `photographers`;
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

--
-- Truncate table before insert `pictures`
--

TRUNCATE TABLE `pictures`;
--
-- Dumping data for table `pictures`
--


INSERT INTO `pictures` (`id`, `approved`, `description`, `filename`, `height`, `hidden`, `price`, `submission_date`, `title`, `width`, `picture_session_id`) VALUES
(1, 'YES', '', '582752.jpg', 1, b'0', '1.00', '2015-10-09 00:00:00', '', 1, 4),
(2, 'YES', 'Genomen tijdens een mooie zonsval.', '58714.jpg', 1, b'0', '1.00', '2015-10-09 00:00:00', 'Kiekje aan het water', 1, 1),
(3, 'YES', 'Mooi totaalbeeld.', '58794.jpg', 1, b'0', '1.00', '2015-10-09 00:00:00', 'Helicopterfoto', 1, 1),
(4, 'YES', '', '582014.jpg', 1, b'0', '1.00', '2015-10-09 00:00:00', '', 1, 5);

--
-- Truncate table before insert `picture_sessions`
--

TRUNCATE TABLE `picture_sessions`;
--
-- Dumping data for table `picture_sessions`
--

INSERT INTO `picture_sessions` (`id`, `code`, `shop_id`, `description`, `title`, `public`) VALUES
(1, 'ardennen-maart-2015#6832', 2, 'Mooie plaatjes uit de Ardennen van de eerste week in maart.', 'Ardennen maart 2015', b'1'),
(2, 'herfstvogels-2015#7201', 4, 'Vogels in de herfst van dit jaar.', 'Herfstvogels 2015', b'1'),
(3, 'fam_zengers_962#0693', 6, 'Familiefoto fam. Zengers', 'Familiefoto fam. Zengers', b'0'),
(4, 'blft-8-2-2015#0538', 8, 'Bruiloft Jannie en Mark', 'Bruiloft Jannie en Mark', b'0'),
(5, 'intocht-sinterklaas#9472', 10, 'Intocht Sinterklaas 2015', 'Intocht Sinterklaas 2015', b'1'),
(6, 'cultentumult2015#5901', 12, 'Cult en Tumult Veldhoven 2015', 'Cult en Tumult Veldhoven 2015', b'1'),
(7, 'uitstapje-bieschbos#9572', 2, 'Ondanks het niet al te mooie weer hebben we het voor elkaar gekregen om toch mooie plaatjes van de fauna in het Bieschbos te schieten.', 'Uitstapje Bieschbos in maart.', b'1');

--
-- Truncate table before insert `producer_accounts`
--

TRUNCATE TABLE `producer_accounts`;
--
-- Dumping data for table `producer_accounts`
--

INSERT INTO `producer_accounts` (`id`, `login`, `password`, `role`) VALUES
(1, 'admin', '1000:69a414cea0f36acc562686eb54d696f25c34a4ea1a519fa9:8945f0cbb0cac0853767cfd498d290df6027d24612059ba5', 'ROLE_ADMIN');

--
-- Truncate table before insert `shops`
--

TRUNCATE TABLE `shops`;
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
SET FOREIGN_KEY_CHECKS=1;
