-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Oct 02, 2015 at 02:10 PM
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
-- Truncate table before insert `photographers`
--

TRUNCATE TABLE `photographers`;
--
-- Dumping data for table `photographers`
--



--
-- Truncate table before insert `pictures`
--

TRUNCATE TABLE `pictures`;
--
-- Dumping data for table `pictures`
--

--
-- Truncate table before insert `shops`
--

TRUNCATE TABLE `shops`;
--
-- Dumping data for table `shops`
--

INSERT INTO `photographers` (`id`, `address`, `city`, `email`, `name`, `phone`) VALUES
(1, 'Molenaar 24', 'Eindhoven', 'info@mooiekiekjes.nl', 'Mooie Kiekjes Eindhoven', '040-9573238');
INSERT INTO `shops` (`id`, `login`, `passwordHash`, `user_id`) VALUES
(2, 'mooiekiekjes-eindhoven', '123', 1);
INSERT INTO `pictures` (`id`, `approved`, `description`, `filename`, `height`, `hidden`, `price`, `submission_date`, `title`, `width`, `shop_id`) VALUES
(3, 'PENDING', 'Weiland gelegen in Zuidlimburg. Zonnig plaatje met veel vee.', 'weiland.jpg', 400, b'0', '10.75', '2015-10-02 14:09:44', 'Weiland in mei.', 500, 2),
(4, 'PENDING', 'Mooi plaatje van gesneden radijsjes. Dank aan tuinier Henk van der Snee voor deze mooie exemplaren.', 'radijsjes.jpg', 400, b'0', '10.75', '2015-10-01 14:09:44', 'Gesneden radijsjes op tafel', 500, 2),
(5, 'PENDING', 'Ingang Biesbosch noordkant, net ten zuiden van Dordrecht.', 'bos.jpg', 400, b'0', '10.75', '2015-09-16 14:09:44', 'Stuk bos in de Biesbosch', 500, 2),
(6, 'YES', 'Weiland gelegen in Zuidlimburg. Zonnig plaatje met veel vee.', 'weiland.jpg', 400, b'0', '10.75', '2015-09-05 14:09:44', 'Weiland in mei.', 500, 2);


SET FOREIGN_KEY_CHECKS=1;
