-- phpMyAdmin SQL Dump
-- version 4.9.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jan 20, 2022 at 01:08 PM
-- Server version: 5.7.23-23
-- PHP Version: 7.3.32

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `incarhlt_newebsitename`
--

-- --------------------------------------------------------

--
-- Table structure for table `add_business_profile`
--

CREATE TABLE `add_business_profile` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `payment_method_type` enum('1','2') NOT NULL COMMENT '1 means card 2 means cash',
  `report_status` enum('1','2') NOT NULL COMMENT '1 means weekly 2 means monthly',
  `type` enum('1','2') NOT NULL COMMENT '1 means family 2 means business',
  `status` enum('1','2') NOT NULL COMMENT '1 means in 2 means out',
  `created_at` timestamp NULL DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `add_business_profile`
--

INSERT INTO `add_business_profile` (`id`, `user_id`, `email`, `payment_method_type`, `report_status`, `type`, `status`, `created_at`) VALUES
(1, 8, 'jitpj7bg234ghfhg@gmail.com', '2', '1', '1', '1', '2019-06-14 08:27:49'),
(2, 9, 'virendmeena112245@gmail.com', '2', '1', '1', '1', '2019-06-18 10:45:02'),
(3, 10, 'xyz@gmail.com', '2', '1', '1', '1', '2019-06-18 13:05:05'),
(4, 11, 'c_dyra@hotmail.com', '2', '1', '1', '1', '2019-07-04 15:23:43'),
(5, 12, 'c_dyra@hotmail.com', '2', '1', '1', '1', '2019-07-04 16:10:25'),
(6, 1, 'User@gmail.com', '2', '1', '1', '1', '2019-08-05 06:54:10'),
(7, 2, 'User@gmail.com', '2', '1', '1', '1', '2019-08-12 05:48:44'),
(8, 3, 'woso8@hotmail.com', '2', '1', '1', '1', '2019-08-17 23:00:44'),
(9, 4, 'Woso8@gmai.com', '2', '1', '1', '1', '2019-08-19 22:01:20'),
(10, 6, 'c_dyra@hotmail.com', '2', '1', '1', '1', '2019-08-27 00:44:59'),
(11, 7, 'woso8@hotmail.com', '2', '1', '1', '1', '2019-09-04 15:53:55'),
(12, 8, 'Yoku@gmail.com', '2', '1', '1', '1', '2019-09-05 11:17:27'),
(13, 9, 'hhhh@gmail.com', '2', '1', '1', '1', '2019-09-05 11:21:41'),
(14, 10, 'hhhgh@gmail.com', '2', '1', '1', '1', '2019-09-05 11:26:30'),
(15, 11, 'hhhgh@gmail.com', '2', '1', '1', '1', '2019-09-05 11:26:35'),
(16, 12, 'htytyhgh@gmail.com', '2', '1', '1', '1', '2019-09-05 11:32:18'),
(17, 13, 'htytehgh@gmail.com', '2', '1', '1', '1', '2019-09-05 11:33:14'),
(18, 14, 'hhhsh@gmail.com', '2', '1', '1', '1', '2019-09-05 11:34:19'),
(19, 15, 'fhdbrhrh@gmail.com', '2', '1', '1', '1', '2019-09-05 11:36:30'),
(20, 16, 'hhdadashh@gmail.com', '2', '1', '1', '1', '2019-09-05 11:39:32'),
(21, 17, 'xbfbfbbfv@gmail.com', '2', '1', '1', '1', '2019-09-05 12:26:33'),
(22, 1, 'virendmeena1122@gmail.com', '2', '1', '1', '1', '2019-09-17 05:15:00'),
(23, 2, 'hhh@gmail.com', '2', '1', '1', '1', '2019-09-17 05:23:25'),
(24, 3, 'Rider@gmail.com', '2', '1', '1', '1', '2019-09-17 05:49:34'),
(25, 4, 'steve.meastros04@gmail.com', '2', '1', '1', '1', '2019-09-18 05:12:17'),
(26, 5, 'steve.meastros04@gmail.com', '2', '1', '1', '1', '2019-09-18 05:16:33'),
(27, 6, 'sdfsdfsf@gmail.com', '2', '1', '1', '1', '2019-09-18 05:49:10'),
(28, 7, 'sdadasdfsdfsf@gmail.com', '2', '1', '1', '1', '2019-09-18 05:57:19'),
(29, 8, 'sdgsg@gmail.com', '2', '1', '1', '1', '2019-09-18 06:22:31'),
(30, 9, 'Newuser@gmail.com', '2', '1', '1', '1', '2019-09-18 06:31:33'),
(31, 10, 'rome@gmail.com', '2', '1', '1', '1', '2019-09-18 06:47:07'),
(32, 11, 'c_dyra@hotmail.com', '2', '1', '1', '1', '2019-09-28 23:35:41'),
(33, 12, 'woso8@hotmail.com', '2', '1', '1', '1', '2019-10-06 02:30:25'),
(34, 1, 'woso8@yahoo.com', '2', '1', '1', '1', '2019-11-30 17:02:26'),
(35, 2, 'shanaa681@gmail.com', '2', '1', '1', '1', '2019-12-04 09:35:16'),
(36, 3, 'Virendmeena1122@gmail.com', '2', '1', '1', '1', '2019-12-05 05:09:26'),
(37, 4, 'jitu.maestros@gmail.com', '2', '1', '1', '1', '2019-12-07 06:41:33'),
(38, 5, 'jitu.maestros1@gmail.com', '2', '1', '1', '1', '2019-12-07 06:44:21'),
(39, 6, 'jitu.maestros2@gmail.com', '2', '1', '1', '1', '2019-12-07 06:49:47'),
(40, 7, 'jitu.maestros@gmail.com', '2', '1', '1', '1', '2019-12-07 07:00:31'),
(41, 8, 'woso8@yahoo.com', '2', '1', '1', '1', '2019-12-20 00:37:23'),
(42, 9, 'raghvend@gmail.com', '2', '1', '1', '1', '2019-12-25 06:33:27'),
(43, 10, 'ruhi@gmail.com', '2', '1', '1', '1', '2019-12-25 08:42:36'),
(44, 11, 'shivangi@gmail.com', '2', '1', '1', '1', '2019-12-25 09:34:50'),
(45, 12, 'asdf@gmail.com', '2', '1', '1', '1', '2020-03-16 08:26:07'),
(46, 13, 'woso8@hotmail.com', '2', '1', '1', '1', '2020-04-02 21:51:57'),
(47, 1, 'woso8@hotmail.com', '2', '1', '1', '1', '2020-05-13 20:44:26'),
(48, 2, 'shahnawaz@gmail.com', '2', '1', '1', '1', '2020-05-18 10:01:32'),
(49, 3, 'jdsj@gmail.com', '2', '1', '1', '1', '2020-05-18 12:38:36'),
(50, 4, 'shahnawaz@gmail.com', '2', '1', '1', '1', '2020-05-27 03:59:33'),
(51, 5, 'Woso8@hotmail.com ', '2', '1', '1', '1', '2020-08-18 02:19:59'),
(52, 6, 'shahnawaz@gmail.com', '2', '1', '1', '1', '2020-09-21 12:27:26'),
(53, 7, 'c_dyra@hotmail.com', '2', '1', '1', '1', '2020-11-27 23:37:06'),
(54, 1, 'woso8@hotmail.com', '2', '1', '1', '1', '2021-03-01 01:34:13'),
(55, 2, 'jitu@gmail.com', '2', '1', '1', '1', '2021-03-20 11:41:23'),
(56, 3, 'Cristinaalberd1802@gmail.com', '2', '1', '1', '1', '2021-08-14 04:19:12'),
(57, 4, 'skywinx08@gmail.com', '2', '1', '1', '1', '2021-10-29 13:06:37');

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `email_id` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `find_driver_range` double NOT NULL,
  `status` enum('1','0') NOT NULL DEFAULT '1' COMMENT '1 - Active, 0 - Deactive',
  `added_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `email_id`, `password`, `find_driver_range`, `status`, `added_on`) VALUES
(1, 'admin@arrive.com', '123', 1000, '1', '2019-06-17 10:36:54');

-- --------------------------------------------------------

--
-- Table structure for table `areas`
--

CREATE TABLE `areas` (
  `id` int(10) UNSIGNED NOT NULL,
  `polygon` polygon NOT NULL,
  `area_name` varchar(70) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE `booking` (
  `id` int(11) NOT NULL,
  `booking_id` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL,
  `driver_id` int(11) NOT NULL,
  `transaction_id` int(11) DEFAULT NULL,
  `start_point` varchar(255) NOT NULL,
  `end_point` varchar(255) NOT NULL,
  `start_point_lat` varchar(255) NOT NULL,
  `start_point_long` varchar(255) NOT NULL,
  `end_point_lat` varchar(255) NOT NULL,
  `end_point_long` varchar(255) NOT NULL,
  `amount` double DEFAULT NULL,
  `tax` double DEFAULT NULL,
  `duration` text,
  `distance` double DEFAULT NULL,
  `promocode` varchar(255) DEFAULT NULL,
  `no_passanger` int(11) DEFAULT NULL,
  `no_luggage` int(11) DEFAULT NULL,
  `mode` enum('0','1','2','3','4','5','6','7','8','9','10','11','12') NOT NULL DEFAULT '0' COMMENT '0-sent, 1-accept, 2-reject, 3-cancel by user, 4-finish, 5-scheduleLater, 6-start ride, 7-arrived, 8- booking automatic rejected, 9- cancel by driver , 10- Finish Ride before rating 11-rate by user 12-rate by driver',
  `cancel_reason` varchar(255) DEFAULT NULL,
  `booking_type` varchar(255) DEFAULT NULL,
  `schedule_date` date DEFAULT NULL,
  `schedule_time` time DEFAULT NULL,
  `otp` varchar(250) DEFAULT NULL,
  `vehicle_type_id` int(11) DEFAULT NULL,
  `vehicle_sub_type_id` int(11) DEFAULT NULL,
  `status` enum('1','0') NOT NULL DEFAULT '1' COMMENT '1 - Active, 0 - Deactive',
  `added_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_on` datetime DEFAULT NULL,
  `l_lat` varchar(200) DEFAULT NULL,
  `l_long` varchar(200) DEFAULT NULL,
  `reject_by_driver` varchar(200) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`id`, `booking_id`, `user_id`, `driver_id`, `transaction_id`, `start_point`, `end_point`, `start_point_lat`, `start_point_long`, `end_point_lat`, `end_point_long`, `amount`, `tax`, `duration`, `distance`, `promocode`, `no_passanger`, `no_luggage`, `mode`, `cancel_reason`, `booking_type`, `schedule_date`, `schedule_time`, `otp`, `vehicle_type_id`, `vehicle_sub_type_id`, `status`, `added_on`, `update_on`, `l_lat`, `l_long`, `reject_by_driver`) VALUES
(1, '01Q05MPJ', 1, 0, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50375366210938', '-81.77859716094565', '28.4311577', '-81.308083', 1272, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-03-20', '12:11:53', '7179', 1, 1, '1', '2021-03-20 12:11:53', NULL, NULL, NULL, NULL),
(2, '3NZZ9Q3A', 1, 0, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50375366210938', '-81.77859716094565', '28.4311577', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-03-20', '12:11:57', '4364', 1, 5, '1', '2021-03-20 12:11:57', NULL, NULL, NULL, NULL),
(3, 'HI3FUHWS', 1, 0, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50375366210938', '-81.77859716094565', '28.4311577', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-03-20', '12:12:19', '3821', 1, 5, '1', '2021-03-20 12:12:19', NULL, NULL, NULL, NULL),
(4, 'UJZG4BA1', 1, 5, NULL, '1700 Oviedo Mall Boulevard, Oviedo, FL 32765, USA', 'Priebe Rd, Clermont, FL 34711, USA', '28.6631446', '-81.23483039999999', '28.5029762', '-81.7772457', 2184, NULL, '55 mins', 72.8, NULL, NULL, NULL, '5', NULL, 'schedule later', '2021-03-25', '08:00:00', '6543', NULL, 3, '1', '2021-05-01 02:09:42', NULL, NULL, NULL, NULL),
(5, 'ZSNAJX1K', 1, 0, NULL, 'Vidina Pl, Oviedo ', 'Priebe Rd, Clermont, FL 34711, USA', '28.65423583984375', '-81.23973533874549', '28.5029762', '-81.77724569999999', 1446, NULL, '56 mins', 72.3, '', 1, NULL, '0', NULL, NULL, '2021-03-24', '17:42:04', '7253', 1, 1, '1', '2021-03-24 17:42:04', NULL, NULL, NULL, NULL),
(6, 'Y0S8NO7W', 1, 0, NULL, 'Vidina Pl, Oviedo ', 'Priebe Rd, Clermont, FL 34711, USA', '28.65423583984375', '-81.23973533874549', '28.5029762', '-81.77724569999999', 1446, NULL, '56 mins', 72.3, '', 1, NULL, '0', NULL, NULL, '2021-03-24', '17:42:20', '1252', 1, 1, '1', '2021-03-24 17:42:20', NULL, NULL, NULL, NULL),
(7, '9I7L4T3K', 1, 0, NULL, 'Vidina Pl, Oviedo ', 'Priebe Rd, Clermont, FL 34711, USA', '28.65423583984375', '-81.23973533874549', '28.5029762', '-81.77724569999999', 1446, NULL, '56 mins', 72.3, '', 1, NULL, '0', NULL, NULL, '2021-03-24', '17:42:29', '7829', 1, 1, '1', '2021-03-24 17:42:29', NULL, NULL, NULL, NULL),
(8, 'RH4SBMGS', 1, 0, NULL, 'Vidina Pl, Oviedo ', 'Priebe Rd, Clermont, FL 34711, USA', '28.65423583984375', '-81.23973533874549', '28.5029762', '-81.77724569999999', 1446, NULL, '56 mins', 72.3, '', 1, NULL, '0', NULL, NULL, '2021-03-24', '17:42:31', '4365', 1, 1, '1', '2021-03-24 17:42:31', NULL, NULL, NULL, NULL),
(9, 'BMZ7ZHYL', 1, 0, NULL, 'Vidina Pl, Oviedo ', 'Priebe Rd, Clermont, FL 34711, USA', '28.65423583984375', '-81.23973533874549', '28.5029762', '-81.77724569999999', 2169, NULL, '56 mins', 72.3, '', 1, NULL, '0', NULL, NULL, '2021-03-24', '17:42:41', '8899', 1, 5, '1', '2021-03-24 17:42:41', NULL, NULL, NULL, NULL),
(10, 'FE65U7OD', 1, 0, NULL, 'Vidina Pl, Oviedo ', 'Priebe Rd, Clermont, FL 34711, USA', '28.65423583984375', '-81.23973533874549', '28.5029762', '-81.77724569999999', 2169, NULL, '56 mins', 72.3, '', 1, NULL, '0', NULL, NULL, '2021-03-24', '17:42:43', '3859', 1, 5, '1', '2021-03-24 17:42:43', NULL, NULL, NULL, NULL),
(11, 'VEOU5BOW', 1, 0, NULL, 'Vidina Pl, Oviedo ', 'Priebe Rd, Clermont, FL 34711, USA', '28.65423583984375', '-81.23973533874549', '28.5029762', '-81.77724569999999', 2169, NULL, '56 mins', 72.3, '', 1, NULL, '0', NULL, NULL, '2021-03-24', '17:42:44', '8497', 1, 5, '1', '2021-03-24 17:42:44', NULL, NULL, NULL, NULL),
(12, '4150CE4R', 1, 0, NULL, 'Vidina Pl, Oviedo ', 'Priebe Rd, Clermont, FL 34711, USA', '28.65423583984375', '-81.23973533874549', '28.5029762', '-81.77724569999999', 2169, NULL, '56 mins', 72.3, '', 1, NULL, '0', NULL, NULL, '2021-03-24', '17:42:49', '3410', 1, 5, '1', '2021-03-24 17:42:49', NULL, NULL, NULL, NULL),
(13, 'F4D3MHO3', 1, 0, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.5037841796875', '-81.77864297820193', '28.4311577', '-81.308083', 1272, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-03-25', '02:49:59', '7219', 1, 1, '1', '2021-03-25 02:49:59', NULL, NULL, NULL, NULL),
(14, '0DWXJYMR', 1, 0, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.5037841796875', '-81.77864297820193', '28.4311577', '-81.308083', 1272, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-03-25', '02:50:01', '5262', 1, 1, '1', '2021-03-25 02:50:01', NULL, NULL, NULL, NULL),
(15, '42HGZ938', 1, 1, NULL, '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '5770 W Irlo Bronson Memorial Hwy, Kissimmee, FL 34746, USA', '28.4311577', '-81.308083', '28.3324313', '-81.5158886', 957, NULL, '24 mins', 31.9, NULL, NULL, NULL, '5', NULL, 'schedule later', '2021-03-30', '14:00:00', '5297', NULL, 3, '1', '2021-07-05 19:15:48', NULL, NULL, NULL, NULL),
(16, 'CH5FT97Q', 1, 0, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50375366210938', '-81.77876223513623', '28.4311577', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-03-28', '18:06:41', '4691', 1, 3, '1', '2021-03-28 18:06:41', NULL, NULL, NULL, NULL),
(17, 'JZUFS6XG', 1, 0, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50375366210938', '-81.77876223513623', '28.4311577', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-03-28', '18:06:45', '5208', 1, 3, '1', '2021-03-28 18:06:45', NULL, NULL, NULL, NULL),
(18, 'P3BCQKE9', 1, 0, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50375366210938', '-81.77876223513623', '28.4311577', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-03-28', '18:06:46', '8068', 1, 3, '1', '2021-03-28 18:06:46', NULL, NULL, NULL, NULL),
(19, 'HY2301S1', 1, 0, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50375366210938', '-81.77876223513623', '28.4311577', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-03-28', '18:06:48', '9115', 1, 3, '1', '2021-03-28 18:06:48', NULL, NULL, NULL, NULL),
(20, '8L748SG2', 1, 0, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50375366210938', '-81.77876223513623', '28.4311577', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-03-28', '18:06:49', '9426', 1, 3, '1', '2021-03-28 18:06:49', NULL, NULL, NULL, NULL),
(21, 'T7X6DK9C', 1, 0, NULL, 'Clermont Town Center, SR-50 W, Clermont ', 'Priebe Rd, Clermont, FL 34711, USA', '28.54730224609375', '-81.7271176827729', '28.5029762', '-81.77724569999999', 218, NULL, '16 mins', 10.9, '', 1, NULL, '0', NULL, NULL, '2021-04-03', '00:56:26', '1113', 1, 1, '1', '2021-04-03 00:56:26', NULL, NULL, NULL, NULL),
(22, 'UIIQGT6K', 1, 0, NULL, 'Clermont Town Center, SR-50 W, Clermont ', 'Priebe Rd, Clermont, FL 34711, USA', '28.54730224609375', '-81.7271176827729', '28.5029762', '-81.77724569999999', 327, NULL, '16 mins', 10.9, '', 1, NULL, '0', NULL, NULL, '2021-04-03', '00:56:29', '1039', 1, 5, '1', '2021-04-03 00:56:29', NULL, NULL, NULL, NULL),
(23, 'QZTXCDSI', 1, 0, NULL, '10915 Priebe Rd, Clermont, FL 34711, USA', 'Orlando International Airport (MCO), Jef', '28.503725700000004', '-81.7785739', '28.431157700000004', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 3, NULL, '0', NULL, NULL, '2021-04-16', '01:44:22', '8967', 1, 4, '1', '2021-04-16 01:44:22', NULL, NULL, NULL, NULL),
(24, '91REZ2WC', 1, 0, NULL, '10915 Priebe Rd, Clermont, FL 34711, USA', 'Orlando International Airport (MCO), Jef', '28.503725700000004', '-81.7785739', '28.431157700000004', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 3, NULL, '0', NULL, NULL, '2021-04-16', '01:44:53', '5032', 1, 4, '1', '2021-04-16 01:44:53', NULL, NULL, NULL, NULL),
(25, 'TGSFGSIZ', 1, 0, NULL, '10915 Priebe Rd, Clermont, FL 34711, USA', 'Orlando International Airport (MCO), Jef', '28.503725700000004', '-81.7785739', '28.431157700000004', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 3, NULL, '0', NULL, NULL, '2021-04-16', '01:45:15', '9785', 1, 4, '1', '2021-04-16 01:45:15', NULL, NULL, NULL, NULL),
(26, 'NRNHIXEH', 1, 0, NULL, '10915 Priebe Rd, Clermont, FL 34711, USA', 'Orlando International Airport (MCO), Jef', '28.503725700000004', '-81.7785739', '28.431157700000004', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 3, NULL, '0', NULL, NULL, '2021-04-16', '01:45:19', '5677', 1, 4, '1', '2021-04-16 01:45:19', NULL, NULL, NULL, NULL),
(27, 'BE01TWYI', 1, 0, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50382995605469', '-81.77867193249214', '28.4311577', '-81.308083', 1272, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-04-16', '01:46:10', '8613', 1, 1, '1', '2021-04-16 01:46:10', NULL, NULL, NULL, NULL),
(28, 'ZGDWGOG8', 1, 0, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50382995605469', '-81.77867193249214', '28.4311577', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-04-16', '01:46:12', '6549', 1, 5, '1', '2021-04-16 01:46:12', NULL, NULL, NULL, NULL),
(29, '11YLKPEZ', 1, 0, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50382995605469', '-81.77867193249214', '28.4311577', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-04-16', '01:46:41', '9889', 1, 5, '1', '2021-04-16 01:46:41', NULL, NULL, NULL, NULL),
(30, 'F1W4QPH1', 1, 0, NULL, '10915 Priebe Rd, Clermont, FL 34711, USA', 'Orlando International Airport (MCO), Jef', '28.503725700000004', '-81.7785739', '28.431157700000004', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 3, NULL, '0', NULL, NULL, '2021-04-16', '01:47:11', '2592', 1, 4, '1', '2021-04-16 01:47:11', NULL, NULL, NULL, NULL),
(31, 'N3UP2MQ3', 1, 0, NULL, '10915 Priebe Rd, Clermont, FL 34711, USA', 'Orlando International Airport (MCO), Jef', '28.503725700000004', '-81.7785739', '28.431157700000004', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 3, NULL, '0', NULL, NULL, '2021-04-16', '01:47:20', '3730', 1, 5, '1', '2021-04-16 01:47:20', NULL, NULL, NULL, NULL),
(32, 'R9Z1B19S', 1, 0, NULL, '10915 Priebe Rd, Clermont, FL 34711, USA', 'Orlando International Airport (MCO), Jef', '28.503725700000004', '-81.7785739', '28.431157700000004', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 3, NULL, '0', NULL, NULL, '2021-04-16', '01:49:02', '2269', 1, 4, '1', '2021-04-16 01:49:02', NULL, NULL, NULL, NULL),
(33, 'INE90614', 1, 0, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50382995605469', '-81.77867193249214', '28.4311577', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-04-16', '01:49:17', '9685', 1, 5, '1', '2021-04-16 01:49:17', NULL, NULL, NULL, NULL),
(34, '3V8HTE0D', 1, 0, NULL, '10915 Priebe Rd, Clermont, FL 34711, USA', 'Orlando International Airport (MCO), Jef', '28.503725700000004', '-81.7785739', '28.431157700000004', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 3, NULL, '0', NULL, NULL, '2021-04-16', '01:57:42', '8287', 1, 5, '1', '2021-04-16 01:57:42', NULL, NULL, NULL, NULL),
(35, '5NWNGL8R', 1, 0, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '3251 Daniels Rd, Orlando, FL 34787, USA', '28.50390625', '-81.7790248878691', '28.5229122', '-81.5828241', 566, NULL, '32 mins', 28.3, '', 1, NULL, '0', NULL, NULL, '2021-04-16', '01:59:49', '7962', 1, 1, '1', '2021-04-16 01:59:49', NULL, NULL, NULL, NULL),
(36, 'KZWV0XAT', 1, 0, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '3251 Daniels Rd, Orlando, FL 34787, USA', '28.50390625', '-81.7790248878691', '28.5229122', '-81.5828241', 849, NULL, '32 mins', 28.3, '', 1, NULL, '0', NULL, NULL, '2021-04-16', '01:59:51', '1248', 1, 5, '1', '2021-04-16 01:59:51', NULL, NULL, NULL, NULL),
(37, 'UGRUYQZ0', 1, 0, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50369262695312', '-81.77881919258181', '28.4311577', '-81.308083', 1272, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-04-16', '22:45:14', '1201', 1, 1, '1', '2021-04-16 22:45:14', NULL, NULL, NULL, NULL),
(38, 'FTE03EXE', 1, 0, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50369262695312', '-81.77881919258181', '28.4311577', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-04-16', '22:45:17', '3127', 1, 4, '1', '2021-04-16 22:45:17', NULL, NULL, NULL, NULL),
(39, 'AD6ZICT3', 1, 0, NULL, '10915 Priebe Rd, Clermont, FL 34711, USA', 'orlando international airport ', '28.503725700000004', '-81.7785739', '28.431157700000004', '-81.308083', 1399.2, NULL, '49 mins', 63.6, '', 3, NULL, '0', NULL, NULL, '2021-04-16', '22:47:44', '4417', 0, 6, '1', '2021-04-16 22:47:44', NULL, NULL, NULL, NULL),
(40, 'LDC8Y08J', 1, 0, NULL, '10915 Priebe Rd, Clermont, FL 34711, USA', 'orlando international airport ', '28.503725700000004', '-81.7785739', '28.431157700000004', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 3, NULL, '0', NULL, NULL, '2021-04-16', '22:47:47', '6513', 1, 4, '1', '2021-04-16 22:47:47', NULL, NULL, NULL, NULL),
(41, 'OS0XUB31', 1, 0, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '2385 S Hwy 27, Clermont, FL 34711, USA', '28.50373840332031', '-81.77865400463416', '28.5373459', '-81.73307679999999', 174, NULL, '15 mins', 8.7, '', 1, NULL, '0', NULL, NULL, '2021-04-18', '20:07:17', '2195', 1, 1, '1', '2021-04-18 20:07:17', NULL, NULL, NULL, NULL),
(42, 'LQKJ7BBB', 1, 0, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50361633300781', '-81.77898258123344', '28.4311577', '-81.308083', 1272, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-04-19', '02:30:25', '9375', 1, 1, '1', '2021-04-19 02:30:25', NULL, NULL, NULL, NULL),
(43, 'JXSBK84J', 1, 0, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50361633300781', '-81.77898258123344', '28.4311577', '-81.308083', 1272, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-04-19', '02:30:48', '2111', 1, 1, '1', '2021-04-19 02:30:48', NULL, NULL, NULL, NULL),
(44, 'CAJ1EUBZ', 1, 0, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50361633300781', '-81.77898258123344', '28.4311577', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-04-19', '02:30:59', '9388', 1, 3, '1', '2021-04-19 02:30:59', NULL, NULL, NULL, NULL),
(45, 'B4Q5XHMY', 1, 0, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50361633300781', '-81.77898258123344', '28.4311577', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-04-19', '02:32:15', '5862', 1, 5, '1', '2021-04-19 02:32:15', NULL, NULL, NULL, NULL),
(46, 'CHBTM30W', 1, 0, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50361633300781', '-81.77898258123344', '28.4311577', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-04-19', '02:32:18', '4194', 1, 5, '1', '2021-04-19 02:32:18', NULL, NULL, NULL, NULL),
(47, 'QT1DWJIL', 1, 0, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50361633300781', '-81.77898258123344', '28.4311577', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-04-19', '02:32:22', '4709', 1, 5, '1', '2021-04-19 02:32:22', NULL, NULL, NULL, NULL),
(48, 'QKGJG6J8', 1, 0, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50361633300781', '-81.77898258123344', '28.4311577', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-04-19', '02:32:25', '6099', 1, 5, '1', '2021-04-19 02:32:25', NULL, NULL, NULL, NULL),
(49, 'AXF03IUW', 1, 0, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50361633300781', '-81.77898258123344', '28.4311577', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-04-19', '02:32:26', '6499', 1, 5, '1', '2021-04-19 02:32:26', NULL, NULL, NULL, NULL),
(50, '8SKXG61T', 1, 0, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50361633300781', '-81.77898258123344', '28.4311577', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-04-19', '02:32:27', '2306', 1, 5, '1', '2021-04-19 02:32:27', NULL, NULL, NULL, NULL),
(51, 'RH9LT4FP', 1, 0, NULL, '10915 Priebe Rd, Clermont, FL 34711, USA', 'orlando international airport ', '28.503725700000004', '-81.7785739', '28.431157700000004', '-81.308083', 1272, NULL, '49 mins', 63.6, '', 3, NULL, '0', NULL, NULL, '2021-04-19', '02:34:58', '7223', 0, 1, '1', '2021-04-19 02:34:58', NULL, NULL, NULL, NULL),
(52, 'TQHBW2FR', 1, 0, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50361633300781', '-81.77898258123344', '28.4311577', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-04-19', '02:35:19', '1071', 1, 5, '1', '2021-04-19 02:35:19', NULL, NULL, NULL, NULL),
(53, 'W9VOOTGF', 1, 0, NULL, '10915 Priebe Rd, Clermont, FL 34711, USA', 'orlando international airport ', '28.503725700000004', '-81.7785739', '28.431157700000004', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 3, NULL, '0', NULL, NULL, '2021-04-19', '02:35:25', '3805', 1, 4, '1', '2021-04-19 02:35:25', NULL, NULL, NULL, NULL),
(54, 'WDZB61KX', 1, 0, NULL, '10915 Priebe Rd, Clermont, FL 34711, USA', 'orlando international airport ', '28.503725700000004', '-81.7785739', '28.431157700000004', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 3, NULL, '0', NULL, NULL, '2021-04-19', '02:35:42', '9133', 1, 4, '1', '2021-04-19 02:35:42', NULL, NULL, NULL, NULL),
(55, 'CNRYNVNE', 1, 0, NULL, '10915 Priebe Rd, Clermont, FL 34711, USA', 'orlando international airport ', '28.503725700000004', '-81.7785739', '28.431157700000004', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 3, NULL, '0', NULL, NULL, '2021-04-19', '02:35:54', '9750', 1, 5, '1', '2021-04-19 02:35:54', NULL, NULL, NULL, NULL),
(56, 'NXEIP6O7', 1, 1, NULL, 'lalghati bhopal', 'mpa nagar', '23.09', '77.50', '23.11', '77.80', 979, NULL, '1 hour 4 mins', 44.5, NULL, 2, NULL, '0', NULL, NULL, '2021-04-19', '07:17:28', '2940', 1, 2, '1', '2021-04-19 07:17:28', NULL, NULL, NULL, NULL),
(57, '5Z8CS49K', 1, 1, NULL, 'lalghati bhopal', 'mpa nagar', '23.09', '77.50', '23.11', '77.80', 979, NULL, '1 hour 4 mins', 44.5, NULL, 2, NULL, '0', NULL, NULL, '2021-04-19', '07:19:12', '2408', 1, 2, '1', '2021-04-19 07:19:12', NULL, NULL, NULL, NULL),
(58, 'N7DOOP63', 1, 1, NULL, 'lalghati bhopal', 'mpa nagar', '23.09', '77.50', '23.11', '77.80', 979, NULL, '1 hour 4 mins', 44.5, NULL, 2, NULL, '0', NULL, NULL, '2021-04-19', '07:22:08', '2202', 1, 2, '1', '2021-04-19 07:22:08', NULL, NULL, NULL, NULL),
(59, 'EJADCXHN', 1, 1, NULL, 'lalghati bhopal', 'mpa nagar', '23.09', '77.50', '23.11', '77.80', 979, NULL, '1 hour 4 mins', 44.5, NULL, 2, NULL, '1', NULL, NULL, '2021-04-19', '07:24:27', '4656', 1, 2, '1', '2021-04-19 07:24:47', NULL, NULL, NULL, NULL),
(60, 'FPIJP54F', 1, 1, NULL, 'lalghati bhopal', 'mpa nagar', '23.09', '77.50', '23.11', '77.80', 979, NULL, '1 hour 4 mins', 44.5, NULL, 2, NULL, '1', NULL, NULL, '2021-04-19', '07:25:13', '1721', 1, 2, '1', '2021-04-19 07:25:31', NULL, NULL, NULL, NULL),
(61, 'OZLJRCOG', 1, 1, NULL, 'lalghati bhopal', 'mpa nagar', '23.09', '77.50', '23.11', '77.80', 979, NULL, '1 hour 4 mins', 44.5, NULL, 2, NULL, '0', NULL, NULL, '2021-04-19', '07:25:38', '6295', 1, 2, '1', '2021-04-19 07:25:38', NULL, NULL, NULL, NULL),
(62, 'R0TIA77P', 1, 1, NULL, 'lalghati bhopal', 'mpa nagar', '23.09', '77.50', '23.11', '77.80', 979, NULL, '1 hour 4 mins', 44.5, NULL, 2, NULL, '0', NULL, NULL, '2021-04-19', '08:08:30', '5415', 1, 2, '1', '2021-04-19 08:09:44', NULL, NULL, NULL, NULL),
(63, 'YB31YQD2', 1, 1, NULL, 'lalghati bhopal', 'mpa nagar', '23.09', '77.50', '23.11', '77.80', 979, NULL, '1 hour 4 mins', 44.5, NULL, 2, NULL, '1', NULL, NULL, '2021-04-19', '09:20:59', '9481', 1, 2, '1', '2021-04-19 09:21:06', NULL, NULL, NULL, NULL),
(64, 'XAW4NVYG', 1, 2, NULL, 'E Altamonte Dr, Altamonte Springs ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.6629638671875', '-81.3619022537429', '28.4311577', '-81.308083', 608, NULL, '38 mins', 30.4, '', 1, NULL, '0', NULL, NULL, '2021-04-19', '13:47:15', '2370', 1, 1, '1', '2021-04-19 13:47:15', NULL, NULL, NULL, NULL),
(65, 'DS4FOFI2', 1, 5, NULL, 'Somerset Shores Ct, Orlando ', 'Priebe Rd, Clermont, FL 34711, USA', '28.45327758789062', '-81.48720847597316', '28.5029762', '-81.77724569999999', 960, NULL, '44 mins', 48, '', 1, NULL, '1', NULL, NULL, '2021-04-19', '20:38:27', '3527', 1, 1, '1', '2021-04-19 20:38:34', NULL, NULL, NULL, NULL),
(66, 'JQEKY68H', 1, 5, NULL, 'Somerset Shores Ct, Orlando ', 'Priebe Rd, Clermont, FL 34711, USA', '28.45327758789062', '-81.48720847597316', '28.5029762', '-81.77724569999999', 960, NULL, '44 mins', 48, '', 1, NULL, '1', NULL, NULL, '2021-04-19', '20:39:12', '9771', 1, 1, '1', '2021-04-19 20:39:21', NULL, NULL, NULL, NULL),
(67, 'E24EUOL1', 1, 5, NULL, 'Somerset Shores Ct, Orlando ', 'Priebe Rd, Clermont, FL 34711, USA', '28.45327758789062', '-81.48720847597316', '28.5029762', '-81.77724569999999', 1440, NULL, '44 mins', 48, '', 1, NULL, '7', NULL, NULL, '2021-04-19', '23:21:41', '2835', 1, 5, '1', '2021-07-05 19:22:51', NULL, NULL, NULL, NULL),
(68, 'DJ327U23', 1, 5, NULL, 'Somerset Shores Ct, Orlando ', 'Priebe Rd, Clermont, FL 34711, USA', '28.45327758789062', '-81.48720847597316', '28.5029762', '-81.77724569999999', 1440, NULL, '44 mins', 48, '', 1, NULL, '1', NULL, NULL, '2021-04-19', '23:49:25', '1148', 1, 3, '1', '2021-04-19 23:49:30', NULL, NULL, NULL, NULL),
(69, 'RPO0GTYJ', 1, 5, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '305 Roseling Crossing, Davenport, FL 33897, USA', '28.50372314453125', '-81.77854682459204', '28.2714927', '-81.6660656', 1086, NULL, '34 mins', 36.2, '', 1, NULL, '1', NULL, NULL, '2021-04-20', '13:57:10', '1633', 1, 5, '1', '2021-04-20 13:57:20', NULL, NULL, NULL, NULL),
(70, '9V5QMD0J', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '305 Roseling Crossing, Davenport, FL 33897, USA', '28.50376892089844', '-81.77854300433026', '28.2714927', '-81.6660656', 724, NULL, '34 mins', 36.2, '', 1, NULL, '0', NULL, NULL, '2021-04-20', '14:01:24', '2180', 1, 1, '1', '2021-04-20 14:01:24', NULL, NULL, NULL, NULL),
(71, '44MN1MOP', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '305 Roseling Crossing, Davenport, FL 33897, USA', '28.50376892089844', '-81.77854300433026', '28.2714927', '-81.6660656', 1086, NULL, '34 mins', 36.2, '', 1, NULL, '0', NULL, NULL, '2021-04-20', '14:01:37', '7016', 1, 5, '1', '2021-04-20 14:01:37', NULL, NULL, NULL, NULL),
(72, 'VA2LDS2F', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50367736816406', '-81.77865153424447', '28.4311577', '-81.308083', 1272, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-04-20', '14:45:39', '6712', 1, 1, '1', '2021-04-20 14:45:39', NULL, NULL, NULL, NULL),
(73, 'Y6RZRH0J', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50367736816406', '-81.77865153424447', '28.4311577', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-04-20', '14:45:55', '7755', 1, 5, '1', '2021-04-20 14:45:55', NULL, NULL, NULL, NULL),
(74, 'EXBGCZXY', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '305 Roseling Crossing, Davenport, FL 33897, USA', '28.50363159179688', '-81.77887747058878', '28.2714927', '-81.6660656', 1086, NULL, '34 mins', 36.2, '', 1, NULL, '0', NULL, NULL, '2021-04-20', '15:46:52', '6911', 1, 5, '1', '2021-04-20 15:46:52', NULL, NULL, NULL, NULL),
(75, 'YCQ1VTAJ', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50376892089844', '-81.7786781048454', '28.4311577', '-81.308083', 1272, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-04-20', '19:54:00', '6184', 1, 1, '1', '2021-04-20 19:54:00', NULL, NULL, NULL, NULL),
(76, '57Y7VFTM', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50376892089844', '-81.7786781048454', '28.4311577', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-04-20', '19:54:14', '7266', 1, 5, '1', '2021-04-20 19:54:14', NULL, NULL, NULL, NULL),
(77, 'M4UM2R8A', 1, 2, NULL, 'Simon Ridge Ct, Kissimmee ', '305 Roseling Crossing, Davenport, FL 33897, USA', '28.29922485351562', '-81.59992943333333', '28.2714927', '-81.6660656', 290, NULL, '17 mins', 14.5, '', 1, NULL, '0', NULL, NULL, '2021-04-20', '20:46:43', '5466', 1, 1, '1', '2021-04-20 20:46:43', NULL, NULL, NULL, NULL),
(78, 'Z3Q08WUX', 1, 2, NULL, 'Simon Ridge Ct, Kissimmee ', '305 Roseling Crossing, Davenport, FL 33897, USA', '28.29922485351562', '-81.59992943333333', '28.2714927', '-81.6660656', 290, NULL, '17 mins', 14.5, '', 1, NULL, '0', NULL, NULL, '2021-04-20', '20:47:27', '6894', 1, 1, '1', '2021-04-20 20:47:27', NULL, NULL, NULL, NULL),
(79, '5Z1K26QW', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '305 Deen Still Rd, Davenport, FL 33897, USA', '28.50379943847656', '-81.77869984113364', '28.2511788', '-81.66274949999999', 768, NULL, '37 mins', 38.4, '', 1, NULL, '0', NULL, NULL, '2021-04-21', '00:30:49', '9497', 1, 1, '1', '2021-04-21 00:30:49', NULL, NULL, NULL, NULL),
(80, '1IK35P9A', 1, 5, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '305 Deen Still Rd, Davenport, FL 33897, USA', '28.50370788574219', '-81.77878964442274', '28.2511788', '-81.66274949999999', 768, NULL, '37 mins', 38.4, '', 1, NULL, '1', NULL, NULL, '2021-04-21', '00:31:33', '1325', 1, 1, '1', '2021-04-21 00:31:41', NULL, NULL, NULL, NULL),
(81, 'PIN17JF4', 1, 5, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '305 Deen Still Rd, Davenport, FL 33897, USA', '28.50370788574219', '-81.77878964442274', '28.2511788', '-81.66274949999999', 768, NULL, '37 mins', 38.4, '', 1, NULL, '1', NULL, NULL, '2021-04-21', '00:32:28', '9892', 1, 1, '1', '2021-04-21 00:32:35', NULL, NULL, NULL, NULL),
(82, '2JEV88P5', 1, 5, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '305 Roseling Crossing, Davenport, FL 33897, USA', '28.5037841796875', '-81.77894837349061', '28.2714927', '-81.6660656', 1086, NULL, '34 mins', 36.2, '', 1, NULL, '1', NULL, NULL, '2021-04-21', '00:34:42', '7164', 1, 5, '1', '2021-04-21 00:34:54', NULL, NULL, NULL, NULL),
(83, 'GY8PQZ2G', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50375366210938', '-81.7786539641436', '28.4311577', '-81.308083', 1272, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-04-21', '00:42:59', '4768', 1, 1, '1', '2021-04-21 00:42:59', NULL, NULL, NULL, NULL),
(84, 'WE7UBPTK', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50375366210938', '-81.7786539641436', '28.4311577', '-81.308083', 1272, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-04-21', '00:43:31', '7852', 1, 1, '1', '2021-04-21 00:43:31', NULL, NULL, NULL, NULL),
(85, 'IAXB737K', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '555 Martin St, Apopka, FL 32712, USA', '28.50379943847656', '-81.77874541106512', '28.692991', '-81.5225612', 936, NULL, '40 mins', 46.8, '', 1, NULL, '0', NULL, NULL, '2021-04-22', '00:54:25', '2495', 1, 1, '1', '2021-04-22 00:54:25', NULL, NULL, NULL, NULL),
(86, 'DL3ILW1S', 2, 1, NULL, 'Bagh Umrao Dulha, Jain Mandir Road, Bhopal ', 'Bhopal, Madhya Pradesh, India', '23.26023864746094', '77.41898538684123', '23.2599333', '77.412615', 20, NULL, '5 mins', 1, '', 1, NULL, '1', NULL, NULL, '2021-04-22', '09:45:29', '2259', 1, 1, '1', '2021-04-22 09:45:42', NULL, NULL, NULL, NULL),
(87, 'IAW40KGP', 2, 1, NULL, 'Bagh Umrao Dulha, Bhopal ', 'Maharana Pratap Nagar, Bhopal, Madhya Pradesh, India', '23.26031494140625', '77.41873279068344', '23.2313434', '77.4326473', 98, NULL, '15 mins', 4.9, '', 1, NULL, '1', NULL, NULL, '2021-04-22', '12:43:58', '3164', 1, 1, '1', '2021-04-22 12:44:05', NULL, NULL, NULL, NULL),
(88, 'A0DYZOLW', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.5037841796875', '-81.77854243155365', '28.4311577', '-81.308083', 1272, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-04-23', '12:06:31', '7820', 1, 1, '1', '2021-04-23 12:06:31', NULL, NULL, NULL, NULL),
(89, 'ZZYBS8AT', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.5037841796875', '-81.77854243155365', '28.4311577', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-04-23', '12:07:15', '9312', 1, 3, '1', '2021-04-23 12:07:15', NULL, NULL, NULL, NULL),
(90, 'OGAAKWP4', 1, 5, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50367736816406', '-81.77879152572194', '28.4311577', '-81.308083', 1272, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-04-24', '02:02:28', '5923', 1, 1, '1', '2021-04-24 02:02:28', NULL, NULL, NULL, NULL),
(91, 'OIO7A2D3', 1, 5, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50367736816406', '-81.77879152572194', '28.4311577', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-04-24', '02:03:36', '4931', 1, 5, '1', '2021-04-24 02:03:36', NULL, NULL, NULL, NULL),
(92, 'NY40OSIE', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50379943847656', '-81.77821645300421', '28.4311577', '-81.308083', 1270, NULL, '49 mins', 63.5, '', 1, NULL, '0', NULL, NULL, '2021-04-24', '02:04:05', '6537', 1, 1, '1', '2021-04-24 02:04:05', NULL, NULL, NULL, NULL),
(93, '9PY3M67M', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50373840332031', '-81.77872042607193', '28.4311577', '-81.308083', 1272, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-04-24', '02:04:36', '8264', 1, 1, '1', '2021-04-24 02:04:36', NULL, NULL, NULL, NULL),
(94, 'K5ZEYXYD', 1, 5, NULL, 'Millenia, Millenia Blvd, Orlando ', 'Priebe Rd, Clermont, FL 34711, USA', '28.48875427246094', '-81.43383776666666', '28.5029762', '-81.77724569999999', 924, NULL, '42 mins', 46.2, '', 1, NULL, '0', NULL, NULL, '2021-04-24', '15:50:36', '4086', 1, 1, '1', '2021-04-24 15:50:36', NULL, NULL, NULL, NULL),
(95, 'H8SCS1R0', 1, 1, NULL, '4200 Conroy Rd, Orlando, FL 32839, USA', 'Priebe Rd, Poygan, WI 54986, USA', '28.4854888', '-81.43152529999999', '44.12971960000001', '-88.7902749', 60, NULL, '19 hours 41 mins', 2, NULL, NULL, NULL, '5', NULL, 'schedule later', '2021-04-24', '13:00:00', '9875', NULL, 3, '1', '2021-04-24 15:52:14', NULL, NULL, NULL, NULL),
(96, 'E1WGL7DU', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50395202636719', '-81.77885226947261', '28.4311577', '-81.308083', 1272, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-04-25', '21:59:36', '8424', 1, 1, '1', '2021-04-25 21:59:36', NULL, NULL, NULL, NULL),
(97, 'FBN0YW4F', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50395202636719', '-81.77885226947261', '28.4311577', '-81.308083', 1272, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-04-25', '22:00:23', '3106', 1, 1, '1', '2021-04-25 22:00:23', NULL, NULL, NULL, NULL),
(98, '05GYIVZ4', 1, 5, NULL, 'Japonica Dr, Orlando ', 'Priebe Rd, Clermont, FL 34711, USA', '28.54350280761719', '-81.29498849912352', '28.5029762', '-81.77724569999999', 1102, NULL, '46 mins', 55.1, '', 1, NULL, '0', NULL, NULL, '2021-04-26', '21:25:46', '8844', 1, 1, '1', '2021-04-26 21:25:46', NULL, NULL, NULL, NULL),
(99, 'GND57YXP', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '305 Roseling Crossing, Davenport, FL 33897, USA', '28.50373840332031', '-81.77876359630409', '28.2714927', '-81.6660656', 724, NULL, '34 mins', 36.2, '', 1, NULL, '0', NULL, NULL, '2021-04-27', '02:23:42', '5759', 1, 1, '1', '2021-04-27 02:23:42', NULL, NULL, NULL, NULL),
(100, 'L2GZ9RNE', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '305 Roseling Crossing, Davenport, FL 33897, USA', '28.50373840332031', '-81.77876359630409', '28.2714927', '-81.6660656', 724, NULL, '34 mins', 36.2, '', 1, NULL, '0', NULL, NULL, '2021-04-27', '02:24:06', '5688', 1, 1, '1', '2021-04-27 02:24:06', NULL, NULL, NULL, NULL),
(101, 'HM92WVQ1', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '305 Roseling Crossing, Davenport, FL 33897, USA', '28.50373840332031', '-81.77876359630409', '28.2714927', '-81.6660656', 1086, NULL, '34 mins', 36.2, '', 1, NULL, '0', NULL, NULL, '2021-04-27', '02:24:22', '1844', 1, 5, '1', '2021-04-27 02:24:22', NULL, NULL, NULL, NULL),
(102, 'IDC1J3U4', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50407409667969', '-81.77872823455024', '28.4311577', '-81.308083', 1274, NULL, '49 mins', 63.7, '', 1, NULL, '0', NULL, NULL, '2021-05-01', '02:09:11', '5031', 1, 1, '1', '2021-05-01 02:09:11', NULL, NULL, NULL, NULL),
(103, '7Y8CPYB6', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50407409667969', '-81.77872823455024', '28.4311577', '-81.308083', 1274, NULL, '49 mins', 63.7, '', 1, NULL, '0', NULL, NULL, '2021-05-01', '02:09:51', '6652', 1, 1, '1', '2021-05-01 02:09:51', NULL, NULL, NULL, NULL),
(104, 'C33PV92J', 1, 5, NULL, 'Curry Ford Rd, Orlando ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.51496887207031', '-81.28939827486704', '28.4311577', '-81.308083', 280, NULL, '17 mins', 14, '', 1, NULL, '0', NULL, NULL, '2021-05-01', '16:58:02', '4022', 1, 1, '1', '2021-05-01 16:58:02', NULL, NULL, NULL, NULL),
(105, 'B95R2UT2', 1, 5, NULL, 'Curry Ford Rd, Orlando ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.51496887207031', '-81.28939827486704', '28.4311577', '-81.308083', 420, NULL, '17 mins', 14, '', 1, NULL, '0', NULL, NULL, '2021-05-01', '16:58:22', '4316', 2, 5, '1', '2021-05-01 16:58:22', NULL, NULL, NULL, NULL),
(106, '5G7ZVO20', 1, 2, NULL, 'Curry Ford Rd, Orlando ', 'Priebe Rd, Clermont, FL 34711, USA', '28.51493835449219', '-81.28945888333332', '28.5029762', '-81.77724569999999', 1749, NULL, '48 mins', 58.3, '', 1, NULL, '0', NULL, NULL, '2021-05-01', '17:19:12', '4525', 1, 5, '1', '2021-05-01 17:19:12', NULL, NULL, NULL, NULL),
(107, 'SPXD91L9', 1, 2, NULL, 'Curry Ford Rd, Orlando ', 'Priebe Rd, Clermont, FL 34711, USA', '28.51493835449219', '-81.28945888333332', '28.5029762', '-81.77724569999999', 1749, NULL, '48 mins', 58.3, '', 1, NULL, '0', NULL, NULL, '2021-05-01', '17:19:49', '4287', 1, 5, '1', '2021-05-01 17:19:49', NULL, NULL, NULL, NULL),
(108, 'EZCWR7PL', 1, 5, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.503662109375', '-81.77869396100144', '28.4311577', '-81.308083', 1272, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-05-02', '02:03:56', '4040', 1, 1, '1', '2021-05-02 02:03:56', NULL, NULL, NULL, NULL),
(109, 'BDBT2BP8', 1, 5, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.503662109375', '-81.77869396100144', '28.4311577', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-05-02', '02:04:15', '6102', 1, 5, '1', '2021-05-02 02:04:15', NULL, NULL, NULL, NULL),
(110, 'QSURDXC6', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.503662109375', '-81.77869396100144', '28.4311577', '-81.308083', 1272, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-05-02', '11:35:53', '8840', 1, 1, '1', '2021-05-02 11:35:53', NULL, NULL, NULL, NULL),
(111, 'D1XA6GNL', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50369262695312', '-81.77876202932923', '28.4311577', '-81.308083', 1272, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-05-03', '00:09:14', '8733', 1, 1, '1', '2021-05-03 00:09:14', NULL, NULL, NULL, NULL),
(112, 'S6MWZFLU', 1, 5, NULL, 'E Semoran Blvd, Apopka ', 'Priebe Rd, Clermont, FL 34711, USA', '28.67263793945312', '-81.47315252121763', '28.5029762', '-81.77724569999999', 1042, NULL, '46 mins', 52.1, '', 1, NULL, '0', NULL, NULL, '2021-05-03', '16:34:03', '3589', 1, 1, '1', '2021-05-03 16:34:03', NULL, NULL, NULL, NULL),
(113, '9KA0VMGF', 1, 5, NULL, 'Beckstrom Dr, Oviedo ', 'Priebe Rd, Clermont, FL 34711, USA', '28.63435363769531', '-81.18957512437703', '28.5029762', '-81.77724569999999', 1582, NULL, '1 hour 4 mins', 79.1, '', 1, NULL, '0', NULL, NULL, '2021-05-03', '21:26:05', '3962', 1, 1, '1', '2021-05-03 21:26:05', NULL, NULL, NULL, NULL),
(114, 'ZGICYO50', 1, 5, NULL, 'Beckstrom Dr, Oviedo ', 'Priebe Rd, Clermont, FL 34711, USA', '28.63435363769531', '-81.18957512437703', '28.5029762', '-81.77724569999999', 1582, NULL, '1 hour 4 mins', 79.1, '', 1, NULL, '0', NULL, NULL, '2021-05-03', '21:26:31', '9713', 1, 1, '1', '2021-05-03 21:26:31', NULL, NULL, NULL, NULL),
(115, 'VLFELZJC', 1, 1, NULL, 'Priebe Rd, Clermont, FL 34711, USA', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.5029762', '-81.7772457', '28.4311577', '-81.308083', 1902, NULL, '48 mins', 63.4, NULL, NULL, NULL, '5', NULL, 'schedule later', '2021-05-04', '18:30:00', '8441', NULL, 3, '1', '2021-05-03 21:27:50', NULL, NULL, NULL, NULL),
(116, '9RZUKG2B', 2, 1, NULL, 'Aish Bagh, Pushpa Nagar Road, Bhopal ', 'Maharana Pratap Nagar, Bhopal, Madhya Pradesh, India', '23.26176452636719', '77.42003630999996', '23.2313434', '77.4326473', 112, NULL, '16 mins', 5.6, '', 1, NULL, '1', NULL, NULL, '2021-05-04', '12:07:56', '8056', 1, 1, '1', '2021-05-04 12:08:14', NULL, NULL, NULL, NULL),
(117, 'XPKHCTKW', 1, 5, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50363159179688', '-81.77880875443415', '28.4311577', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-05-04', '19:41:26', '4118', 1, 4, '1', '2021-05-04 19:41:26', NULL, NULL, NULL, NULL),
(118, 'IG1MJ9GX', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50363159179688', '-81.77880875443415', '28.4311577', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-05-04', '19:42:16', '5296', 1, 4, '1', '2021-05-04 19:42:16', NULL, NULL, NULL, NULL),
(119, 'V3LJF80R', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50363159179688', '-81.77880875443415', '28.4311577', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-05-04', '20:23:30', '2753', 1, 4, '1', '2021-05-04 20:23:30', NULL, NULL, NULL, NULL),
(120, 'VG2Z3EQX', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', 'Orlando, FL, USA', '28.50379943847656', '-81.77855780205324', '28.3771857', '-81.57074', 966, NULL, '46 mins', 48.3, '', 1, NULL, '0', NULL, NULL, '2021-05-05', '00:23:12', '4676', 1, 1, '1', '2021-05-05 00:23:12', NULL, NULL, NULL, NULL),
(121, '84DOD7EP', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', 'Orlando, FL, USA', '28.50379943847656', '-81.77855780205324', '28.3771857', '-81.57074', 1449, NULL, '46 mins', 48.3, '', 1, NULL, '0', NULL, NULL, '2021-05-05', '00:23:25', '5331', 2, 5, '1', '2021-05-05 00:23:25', NULL, NULL, NULL, NULL),
(122, '707QAXFH', 1, 5, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1200 Red Cleveland Blvd, Sanford, FL 32773, USA', '28.50367736816406', '-81.77874962786454', '28.7759419', '-81.2342888', 1792, NULL, '1 hour 5 mins', 89.6, '', 1, NULL, '0', NULL, NULL, '2021-05-05', '15:27:15', '6336', 1, 1, '1', '2021-05-05 15:27:15', NULL, NULL, NULL, NULL),
(123, '7M074017', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50379943847656', '-81.77874183707566', '28.4311577', '-81.308083', 1272, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-05-08', '11:57:48', '6143', 1, 1, '1', '2021-05-08 11:57:48', NULL, NULL, NULL, NULL),
(124, '7A4597L7', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50379943847656', '-81.77874183707566', '28.4311577', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-05-08', '11:58:42', '4134', 1, 3, '1', '2021-05-08 11:58:42', NULL, NULL, NULL, NULL),
(125, 'HTHCL8VQ', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '820 E Altamonte Dr, Altamonte Springs, FL 32701, USA', '28.5037841796875', '-81.77837913239658', '28.6627732', '-81.3618972', 1926, NULL, '50 mins', 64.2, '', 1, NULL, '0', NULL, NULL, '2021-05-08', '12:10:10', '5162', 1, 3, '1', '2021-05-08 12:10:10', NULL, NULL, NULL, NULL),
(126, 'B8HEHFKZ', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '820 E Altamonte Dr, Altamonte Springs, FL 32701, USA', '28.5037841796875', '-81.77837913239658', '28.6627732', '-81.3618972', 1926, NULL, '50 mins', 64.2, '', 1, NULL, '0', NULL, NULL, '2021-05-08', '16:30:12', '3978', 1, 3, '1', '2021-05-08 16:30:12', NULL, NULL, NULL, NULL),
(127, '0MTA3WH1', 1, 5, NULL, 'Clermont Town Center, SR-50 W, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.54727172851562', '-81.72717604762353', '28.4311577', '-81.308083', 1062, NULL, '39 mins', 53.1, '', 1, NULL, '0', NULL, NULL, '2021-05-08', '16:33:14', '5508', 1, 1, '1', '2021-05-08 16:33:14', NULL, NULL, NULL, NULL),
(128, '6UXGOIHP', 1, 2, NULL, 'Clermont Town Center, SR-50 W, Clermont ', 'Orlando, FL, USA', '28.5472412109375', '-81.72716563139709', '28.5383355', '-81.3792365', 848, NULL, '32 mins', 42.4, '', 1, NULL, '0', NULL, NULL, '2021-05-08', '16:33:50', '7366', 1, 1, '1', '2021-05-08 16:33:50', NULL, NULL, NULL, NULL),
(129, 'TZDD18DZ', 1, 5, NULL, 'Clermont Town Center, SR-50 W, Clermont ', 'Orlando, FL, USA', '28.5472412109375', '-81.72716563139709', '28.5383355', '-81.3792365', 848, NULL, '32 mins', 42.4, '', 1, NULL, '0', NULL, NULL, '2021-05-08', '16:34:16', '8544', 1, 1, '1', '2021-05-08 16:34:16', NULL, NULL, NULL, NULL),
(130, '2KVIII44', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50375366210938', '-81.77887609996148', '28.4311577', '-81.308083', 1272, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-05-09', '18:30:36', '1496', 1, 1, '1', '2021-05-09 18:30:36', NULL, NULL, NULL, NULL),
(131, 'UOLU9AUP', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50375366210938', '-81.77887609996148', '28.4311577', '-81.308083', 1272, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-05-09', '18:31:01', '6898', 1, 1, '1', '2021-05-09 18:31:01', NULL, NULL, NULL, NULL),
(132, 'MZF1TR42', 1, 5, NULL, 'Westwood, Westwood Blvd, Orlando ', 'Priebe Rd, Clermont, FL 34711, USA', '28.42039489746094', '-81.46079905128605', '28.5029762', '-81.77724569999999', 1034, NULL, '44 mins', 51.7, '', 1, NULL, '0', NULL, NULL, '2021-05-09', '19:36:55', '9775', 1, 1, '1', '2021-05-09 19:36:55', NULL, NULL, NULL, NULL),
(133, 'RIPCONOV', 1, 5, NULL, 'Saxon Blvd, Deltona ', 'Priebe Rd, Clermont, FL 34711, USA', '28.91029357910156', '-81.26694488137112', '28.5029762', '-81.77724569999999', 1854, NULL, '1 hour 7 mins', 92.7, '', 1, NULL, '0', NULL, NULL, '2021-05-10', '19:47:03', '5415', 1, 1, '1', '2021-05-10 19:47:03', NULL, NULL, NULL, NULL),
(134, 'GKMINROL', 1, 5, NULL, 'Queen Palm Dr, Apopka ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.69691467285156', '-81.47984249999999', '28.4311577', '-81.308083', 1196, NULL, '46 mins', 59.8, '', 1, NULL, '0', NULL, NULL, '2021-05-18', '17:19:44', '8077', 1, 1, '1', '2021-05-18 17:19:44', NULL, NULL, NULL, NULL),
(135, 'CYZN7EDT', 1, 5, NULL, 'Queen Palm Dr, Apopka ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.69691467285156', '-81.47984249999999', '28.4311577', '-81.308083', 1794, NULL, '46 mins', 59.8, '', 1, NULL, '0', NULL, NULL, '2021-05-18', '17:20:03', '7747', 1, 5, '1', '2021-05-18 17:20:03', NULL, NULL, NULL, NULL),
(136, 'RT4Y5D4K', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.5037841796875', '-81.77865948855006', '28.4311577', '-81.308083', 1272, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-05-22', '02:18:03', '6965', 1, 1, '1', '2021-05-22 02:18:03', NULL, NULL, NULL, NULL),
(137, 'AEL35GC2', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.5037841796875', '-81.77865948855006', '28.4311577', '-81.308083', 1272, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-05-22', '02:18:27', '6101', 1, 1, '1', '2021-05-22 02:18:27', NULL, NULL, NULL, NULL),
(138, 'BF6SRI9X', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.5037841796875', '-81.77865948855006', '28.4311577', '-81.308083', 1908, NULL, '49 mins', 63.6, '', 1, NULL, '0', NULL, NULL, '2021-05-22', '02:18:36', '9553', 1, 5, '1', '2021-05-22 02:18:36', NULL, NULL, NULL, NULL),
(139, 'D0HLXG9Q', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '5770 W Irlo Bronson Memorial Hwy, Kissimmee, FL 34746, USA', '28.50381469726562', '-81.77860985969032', '28.3324313', '-81.5158886', 876, NULL, '43 mins', 43.8, '', 1, NULL, '0', NULL, NULL, '2021-05-31', '04:11:53', '6514', 1, 1, '1', '2021-05-31 04:11:53', NULL, NULL, NULL, NULL),
(140, 'A29YBFO8', 1, 2, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '5770 W Irlo Bronson Memorial Hwy, Kissimmee, FL 34746, USA', '28.50381469726562', '-81.77860985969032', '28.3324313', '-81.5158886', 1314, NULL, '43 mins', 43.8, '', 1, NULL, '0', NULL, NULL, '2021-05-31', '04:12:12', '5726', 1, 3, '1', '2021-05-31 04:12:12', NULL, NULL, NULL, NULL),
(141, '0L001S3R', 1, 1, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50381469726562', '-81.77870158625421', '28.4311577', '-81.308083', 1272, NULL, '50 mins', 63.6, '', 1, NULL, '1', NULL, NULL, '2021-06-02', '16:03:53', '5514', 1, 1, '1', '2021-06-02 16:04:00', NULL, NULL, NULL, NULL),
(142, '99ZLO3Q2', 1, 1, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1200 Red Cleveland Blvd, Sanford, FL 32773, USA', '28.50382995605469', '-81.77869652683628', '28.7759419', '-81.2342888', 1792, NULL, '1 hour 6 mins', 89.6, '', 1, NULL, '1', NULL, NULL, '2021-06-02', '16:04:47', '3875', 1, 1, '1', '2021-06-02 16:04:51', NULL, NULL, NULL, NULL),
(143, '7NONCOGP', 1, 1, NULL, 'Priebe Rd, Clermont, FL 34711, USA', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.5029762', '-81.7772457', '28.4311577', '-81.308083', 1902, NULL, '49 mins', 63.4, NULL, NULL, NULL, '5', NULL, 'schedule later', '2021-06-04', '12:00:00', '5229', NULL, 3, '1', '2021-06-02 16:12:03', NULL, NULL, NULL, NULL),
(144, 'W6A24D6Q', 1, 1, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '4100 George J Bean Pkwy, Tampa, FL 33607, USA', '28.50373840332031', '-81.77869137943448', '27.9771524', '-82.5311382', 2460, NULL, '1 hour 22 mins', 123, '', 1, NULL, '1', NULL, NULL, '2021-06-02', '16:34:37', '2881', 1, 1, '1', '2021-06-02 16:34:44', NULL, NULL, NULL, NULL),
(145, 'YQ2Z7XY6', 1, 1, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '4100 George J Bean Pkwy, Tampa, FL 33607, USA', '28.50373840332031', '-81.77869137943448', '27.9771524', '-82.5311382', 3690, NULL, '1 hour 22 mins', 123, '', 1, NULL, '1', NULL, NULL, '2021-06-02', '16:38:03', '8866', 1, 5, '1', '2021-06-02 16:38:08', NULL, NULL, NULL, NULL),
(146, 'RERKSWKB', 1, 1, NULL, 'Oakley Seaver Dr, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.54557800292969', '-81.72802992014896', '28.4311577', '-81.308083', 1054, NULL, '37 mins', 52.7, '', 1, NULL, '1', NULL, NULL, '2021-06-05', '00:26:55', '9886', 1, 1, '1', '2021-06-05 00:27:00', NULL, NULL, NULL, NULL),
(147, 'FCN7CA4J', 1, 1, NULL, 'Oakley Seaver Dr, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.54557800292969', '-81.72802992014896', '28.4311577', '-81.308083', 1581, NULL, '37 mins', 52.7, '', 1, NULL, '1', NULL, NULL, '2021-06-05', '00:30:48', '2937', 1, 5, '1', '2021-06-05 00:30:52', NULL, NULL, NULL, NULL),
(148, 'HB3K9C20', 1, 1, NULL, 'Priebe Rd, Clermont, FL 34711, USA', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.5029762', '-81.7772457', '28.4311577', '-81.308083', 1902, NULL, '49 mins', 63.4, NULL, NULL, NULL, '5', NULL, 'schedule later', '2021-06-05', '09:00:00', '5596', NULL, 5, '1', '2021-06-05 01:02:43', NULL, NULL, NULL, NULL),
(149, 'P1OWDZ30', 1, 1, NULL, 'Spinnaker Cir, South Daytona ', 'Priebe Rd, Clermont, FL 34711, USA', '29.15284729003906', '-81.00540894800267', '28.5029762', '-81.77724569999999', 2700, NULL, '1 hour 35 mins', 135, '', 1, NULL, '1', NULL, NULL, '2021-06-05', '17:43:39', '5715', 1, 1, '1', '2021-06-05 17:43:45', NULL, NULL, NULL, NULL);
INSERT INTO `booking` (`id`, `booking_id`, `user_id`, `driver_id`, `transaction_id`, `start_point`, `end_point`, `start_point_lat`, `start_point_long`, `end_point_lat`, `end_point_long`, `amount`, `tax`, `duration`, `distance`, `promocode`, `no_passanger`, `no_luggage`, `mode`, `cancel_reason`, `booking_type`, `schedule_date`, `schedule_time`, `otp`, `vehicle_type_id`, `vehicle_sub_type_id`, `status`, `added_on`, `update_on`, `l_lat`, `l_long`, `reject_by_driver`) VALUES
(150, 'PT28FDD8', 1, 0, NULL, 'Punta Cana ', 'Carretera Aeropuerto, Punta Cana 23000, Dominican Republic', '18.72286987304688', '-68.45909573950624', '18.5632813', '-68.36849310000001', 810, NULL, '31 mins', 27, '', 1, NULL, '0', NULL, NULL, '2021-06-07', '00:59:01', '2187', 1, 5, '1', '2021-06-07 00:59:01', NULL, NULL, NULL, NULL),
(151, 'DG4UEGFR', 1, 1, NULL, 'Punta Cana ', 'Carretera Aeropuerto, Punta Cana 23000, Dominican Republic', '18.72286987304688', '-68.45909573950624', '18.5632813', '-68.36849310000001', 810, NULL, '31 mins', 27, '', 1, NULL, '1', NULL, NULL, '2021-06-07', '00:59:15', '1294', 1, 5, '1', '2021-06-07 00:59:21', NULL, NULL, NULL, NULL),
(152, 'Y0HCSK1H', 1, 1, NULL, 'Punta Cana ', 'Carretera Aeropuerto, Punta Cana 23000, Dominican Republic', '18.7230224609375', '-68.4590534030652', '18.5632813', '-68.36849310000001', 810, NULL, '31 mins', 27, '', 1, NULL, '1', NULL, NULL, '2021-06-07', '01:03:07', '4442', 1, 5, '1', '2021-06-07 01:03:11', NULL, NULL, NULL, NULL),
(153, '2W386UD2', 1, 1, NULL, 'Cabarete, Dominican Republic', 'Carretera Aeropuerto, Punta Cana 23000, Dominican Republic', '19.7509228', '-70.4144209', '18.5632813', '-68.36849310000001', 8020, NULL, '5 hours 29 mins', 401, '', 1, NULL, '1', NULL, NULL, '2021-06-07', '01:04:15', '3276', 1, 1, '1', '2021-06-07 01:04:19', NULL, NULL, NULL, NULL),
(154, 'J8396BGE', 1, 1, NULL, 'Punta Cana ', 'Carretera Aeropuerto, Punta Cana 23000, Dominican Republic', '18.72286987304688', '-68.45907783863322', '18.5632813', '-68.36849310000001', 810, NULL, '31 mins', 27, '', 1, NULL, '1', NULL, NULL, '2021-06-08', '00:52:19', '9290', 1, 5, '1', '2021-06-08 00:52:28', NULL, NULL, NULL, NULL),
(155, 'VBC5AQ6E', 1, 1, NULL, 'Punta Cana ', 'Cap Cana, Punta Cana 23000, Dominican Republic', '18.72279357910156', '-68.45945411653416', '18.482462', '-68.4093158', 674, NULL, '36 mins', 33.7, '', 1, NULL, '1', NULL, NULL, '2021-06-09', '02:19:19', '4535', 1, 1, '1', '2021-06-09 02:19:25', NULL, NULL, NULL, NULL),
(156, '7Z9A0Q35', 1, 1, NULL, 'Punta Cana ', 'Punta Cana 23301, Dominican Republic', '18.72276306152344', '-68.4594446240221', '18.6693603', '-68.4266964', 240, NULL, '21 mins', 12, '', 1, NULL, '1', NULL, NULL, '2021-06-09', '02:20:57', '4029', 1, 1, '1', '2021-06-09 02:21:02', NULL, NULL, NULL, NULL),
(157, 'CHWROYNR', 1, 0, NULL, 'Punta Cana ', 'Sosua Beach, 57000, Dominican Republic', '18.72279357910156', '-68.45945138425267', '19.7572211', '-70.51715039999999', 8380, NULL, '5 hours 53 mins', 419, '', 1, NULL, '0', NULL, NULL, '2021-06-09', '02:21:59', '8050', 1, 1, '1', '2021-06-09 02:21:59', NULL, NULL, NULL, NULL),
(158, '1P1IZAQO', 1, 0, NULL, 'Punta Cana ', 'Sosua Beach, 57000, Dominican Republic', '18.72279357910156', '-68.45945138425267', '19.7572211', '-70.51715039999999', 12570, NULL, '5 hours 53 mins', 419, '', 1, NULL, '0', NULL, NULL, '2021-06-09', '02:22:01', '1859', 1, 5, '1', '2021-06-09 02:22:01', NULL, NULL, NULL, NULL),
(159, 'L6A8UQZI', 1, 0, NULL, 'Punta Cana ', 'Sosua Beach, 57000, Dominican Republic', '18.72279357910156', '-68.45945138425267', '19.7572211', '-70.51715039999999', 12570, NULL, '5 hours 53 mins', 419, '', 1, NULL, '0', NULL, NULL, '2021-06-09', '02:22:05', '6164', 1, 5, '1', '2021-06-09 02:22:05', NULL, NULL, NULL, NULL),
(160, 'ICVZ5SPY', 1, 1, NULL, 'Punta Cana ', 'Sosua Beach, 57000, Dominican Republic', '18.72279357910156', '-68.45945138425267', '19.7572211', '-70.51715039999999', 12570, NULL, '5 hours 53 mins', 419, '', 1, NULL, '1', NULL, NULL, '2021-06-09', '02:22:19', '5960', 1, 5, '1', '2021-06-09 02:22:22', NULL, NULL, NULL, NULL),
(161, '0LLNMVV4', 1, 1, NULL, 'Punta Cana ', 'Cabarete, Dominican Republic', '18.72404479980469', '-68.45791449229861', '19.7509228', '-70.4144209', 8120, NULL, '5 hours 47 mins', 406, '', 1, NULL, '1', NULL, NULL, '2021-06-09', '18:49:04', '5420', 1, 1, '1', '2021-06-09 18:49:07', NULL, NULL, NULL, NULL),
(162, 'LHH5P3XK', 1, 1, NULL, 'Punta Cana ', 'Cabarete 57000, Dominican Republic', '18.72463989257812', '-68.4581286776515', '19.7491476', '-70.4156303', 8200, NULL, '5 hours 54 mins', 410, '', 1, NULL, '1', NULL, NULL, '2021-06-10', '00:53:33', '8813', 1, 1, '1', '2021-06-10 00:53:35', NULL, NULL, NULL, NULL),
(163, '3AP0L7VD', 1, 0, NULL, 'Punta Cana ', 'esquina Carretera de Juanillo, Blvd. Turístico del Este, Punta Cana 23000, Dominican Republic', '18.72317504882812', '-68.45890450556533', '18.5570147', '-68.38304289999999', 504, NULL, '27 mins', 25.2, '', 1, NULL, '0', NULL, NULL, '2021-06-12', '14:35:27', '9182', 1, 1, '1', '2021-06-12 14:35:27', NULL, NULL, NULL, NULL),
(164, 'NWH922OU', 1, 0, NULL, 'Punta Cana ', 'esquina Carretera de Juanillo, Blvd. Turístico del Este, Punta Cana 23000, Dominican Republic', '18.72317504882812', '-68.45890450556533', '18.5570147', '-68.38304289999999', 756, NULL, '27 mins', 25.2, '', 1, NULL, '0', NULL, NULL, '2021-06-12', '14:35:31', '4169', 1, 5, '1', '2021-06-12 14:35:31', NULL, NULL, NULL, NULL),
(165, 'FA4MC8Y3', 1, 1, NULL, 'Punta Cana ', 'esquina Carretera de Juanillo, Blvd. Turístico del Este, Punta Cana 23000, Dominican Republic', '18.72317504882812', '-68.45890450556533', '18.5570147', '-68.38304289999999', 756, NULL, '27 mins', 25.2, '', 1, NULL, '1', NULL, NULL, '2021-06-12', '14:35:54', '2819', 1, 5, '1', '2021-06-12 14:36:02', NULL, NULL, NULL, NULL),
(166, 'DT47ZJX7', 1, 1, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50376892089844', '-81.77867156850382', '28.4311577', '-81.308083', 1272, NULL, '50 mins', 63.6, '', 1, NULL, '1', NULL, NULL, '2021-06-16', '19:16:35', '2767', 1, 1, '1', '2021-06-16 19:16:39', NULL, NULL, NULL, NULL),
(167, 'Q7ZC41CU', 1, 1, NULL, 'Prairie Meadows Dr, Orlando ', 'Priebe Rd, Clermont, FL 34711, USA', '28.37446594238281', '-81.39486638333334', '28.5029762', '-81.77724569999999', 1803, NULL, '50 mins', 60.1, '', 1, NULL, '1', NULL, NULL, '2021-06-17', '21:21:13', '3932', 1, 5, '1', '2021-06-17 21:21:18', NULL, NULL, NULL, NULL),
(168, '2TOMQY9D', 1, 1, NULL, 'Priebe Rd, Clermont, FL 34711, USA', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.5029762', '-81.7772457', '28.4311577', '-81.308083', 1902, NULL, '49 mins', 63.4, NULL, NULL, NULL, '5', NULL, 'schedule later', '2021-06-19', '07:30:00', '9269', NULL, 5, '1', '2021-06-17 21:23:49', NULL, NULL, NULL, NULL),
(169, '1LWKK36K', 1, 1, NULL, 'US-27 N, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.53712463378906', '-81.73674519438781', '28.4311577', '-81.308083', 1100, NULL, '40 mins', 55, '', 1, NULL, '1', NULL, NULL, '2021-07-03', '00:38:34', '1004', 1, 1, '1', '2021-07-03 00:38:39', NULL, NULL, NULL, NULL),
(170, 'YKMLSHI2', 1, 1, NULL, 'US-27 N, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.53656005859375', '-81.73473275078442', '28.4311577', '-81.308083', 1632, NULL, '39 mins', 54.4, '', 1, NULL, '1', NULL, NULL, '2021-07-04', '16:08:48', '3894', 1, 3, '1', '2021-07-04 16:08:58', NULL, NULL, NULL, NULL),
(171, 'CSUW42KM', 1, 1, NULL, 'Priebe Rd, Clermont, FL 34711, USA', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.5029762', '-81.7772457', '28.4311577', '-81.308083', 1902, NULL, '49 mins', 63.4, NULL, NULL, NULL, '5', NULL, 'schedule later', '2021-07-05', '07:30:00', '1386', NULL, 5, '1', '2021-07-04 16:12:24', NULL, NULL, NULL, NULL),
(172, 'SONC8FE7', 1, 1, NULL, 'US-27 N, Clermont ', 'Winter Garden, FL 34787, USA', '28.53648376464844', '-81.73477753879935', '28.5652787', '-81.58618469999999', 336, NULL, '21 mins', 16.8, '', 1, NULL, '1', NULL, NULL, '2021-07-04', '16:15:04', '6334', 1, 1, '1', '2021-07-04 16:15:09', NULL, NULL, NULL, NULL),
(173, 'XCDTIMRC', 1, 1, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '4951 International Dr, Orlando, FL 32819, USA', '28.50379943847656', '-81.77866440105011', '28.4737689', '-81.452285', 930, NULL, '42 mins', 46.5, '', 1, NULL, '1', NULL, NULL, '2021-07-05', '19:13:03', '9292', 1, 1, '1', '2021-07-05 19:13:22', NULL, NULL, NULL, NULL),
(174, 'RXKDOXG5', 1, 1, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '4951 International Dr, Orlando, FL 32819, USA', '28.50379943847656', '-81.77866440105011', '28.4737689', '-81.452285', 930, NULL, '42 mins', 46.5, '', 1, NULL, '1', NULL, NULL, '2021-07-05', '19:16:21', '5508', 1, 1, '1', '2021-07-05 19:16:25', NULL, NULL, NULL, NULL),
(175, '67LYYRDZ', 1, 1, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '4951 International Dr, Orlando, FL 32819, USA', '28.50379943847656', '-81.77866440105011', '28.4737689', '-81.452285', 930, NULL, '42 mins', 46.5, '', 1, NULL, '1', NULL, NULL, '2021-07-05', '19:22:21', '4208', 1, 1, '1', '2021-07-05 19:22:31', NULL, NULL, NULL, NULL),
(176, '8K0F4NPV', 1, 1, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '4951 International Dr, Orlando, FL 32819, USA', '28.50379943847656', '-81.77866440105011', '28.4737689', '-81.452285', 1395, NULL, '42 mins', 46.5, '', 1, NULL, '1', NULL, NULL, '2021-07-05', '19:25:30', '3054', 1, 5, '1', '2021-07-05 19:25:39', NULL, NULL, NULL, NULL),
(177, 'U0FHNG8R', 1, 1, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '4100 George J Bean Pkwy, Tampa, FL 33607, USA', '28.50387573242188', '-81.77864177957137', '27.9771524', '-82.5311382', 2728, NULL, '1 hour 22 mins', 124, '', 1, NULL, '1', NULL, NULL, '2021-07-05', '19:38:39', '9918', 5, 15, '1', '2021-07-05 19:38:45', NULL, NULL, NULL, NULL),
(178, 'WN80EQPZ', 1, 1, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '4100 George J Bean Pkwy, Tampa, FL 33607, USA', '28.50387573242188', '-81.77864177957137', '27.9771524', '-82.5311382', 2728, NULL, '1 hour 22 mins', 124, '', 1, NULL, '1', NULL, NULL, '2021-07-05', '19:38:55', '6156', 5, 15, '1', '2021-07-05 19:38:58', NULL, NULL, NULL, NULL),
(179, 'UB0K5I7K', 1, 1, NULL, 'Martin\'s Landing, Priebe Rd, Clermont ', '1 Jeff Fuqua Blvd, Orlando, FL 32827, USA', '28.50364685058594', '-81.77863564378599', '28.4311577', '-81.308083', 1272, NULL, '49 mins', 63.6, '', 1, NULL, '1', NULL, NULL, '2021-07-19', '01:12:18', '8980', 1, 1, '1', '2021-07-19 01:12:22', NULL, NULL, NULL, NULL),
(180, 'X7E2TKON', 1, 0, NULL, 'Punta Cana ', 'Ent. Casino, Punta Cana 23000, Dominican Republic', '18.70918273925781', '-68.44585018701149', '18.6867353', '-68.4231681', 114, NULL, '15 mins', 5.7, '', 1, NULL, '0', NULL, NULL, '2021-08-31', '14:03:24', '2021', 1, 1, '1', '2021-08-31 14:03:24', NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `booking_driver`
--

CREATE TABLE `booking_driver` (
  `id` int(11) NOT NULL,
  `booking_id` int(11) NOT NULL,
  `driver_id` int(11) NOT NULL,
  `mode` enum('0','1','2','3','4','5','6','7','8','9') NOT NULL DEFAULT '0' COMMENT '0-sent, 1-accept, 2-reject, 3-cancel by user, 4-finish, 5-scheduleLater, 6-start ride, 7-arrived, 8- booking automatic rejected, 9- cancel by driver',
  `status` enum('1','0') NOT NULL DEFAULT '1' COMMENT '1-active, 0-deactive',
  `added_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `booking_driver`
--

INSERT INTO `booking_driver` (`id`, `booking_id`, `driver_id`, `mode`, `status`, `added_on`) VALUES
(1, 56, 1, '0', '1', '2021-04-19 07:17:28'),
(2, 57, 1, '0', '1', '2021-04-19 07:19:12'),
(3, 58, 1, '0', '1', '2021-04-19 07:22:08'),
(4, 59, 1, '1', '1', '2021-04-19 07:24:47'),
(5, 60, 1, '1', '1', '2021-04-19 07:25:31'),
(6, 61, 1, '0', '1', '2021-04-19 07:25:38'),
(7, 62, 4, '0', '1', '2021-04-19 08:08:30'),
(8, 63, 1, '1', '1', '2021-04-19 09:21:06'),
(9, 64, 2, '0', '1', '2021-04-19 13:47:15'),
(10, 65, 5, '1', '1', '2021-04-19 20:38:34'),
(11, 66, 5, '1', '1', '2021-04-19 20:39:21'),
(12, 67, 5, '1', '1', '2021-04-19 23:21:49'),
(13, 68, 5, '1', '1', '2021-04-19 23:49:30'),
(14, 69, 5, '1', '1', '2021-04-20 13:57:20'),
(15, 70, 2, '0', '1', '2021-04-20 14:01:24'),
(16, 71, 2, '0', '1', '2021-04-20 14:01:37'),
(17, 72, 2, '0', '1', '2021-04-20 14:45:39'),
(18, 73, 2, '0', '1', '2021-04-20 14:45:55'),
(19, 74, 2, '0', '1', '2021-04-20 15:46:52'),
(20, 75, 2, '0', '1', '2021-04-20 19:54:00'),
(21, 76, 2, '0', '1', '2021-04-20 19:54:14'),
(22, 77, 2, '0', '1', '2021-04-20 20:46:43'),
(23, 78, 2, '0', '1', '2021-04-20 20:47:27'),
(24, 79, 2, '0', '1', '2021-04-21 00:30:49'),
(25, 80, 5, '1', '1', '2021-04-21 00:31:41'),
(26, 81, 5, '1', '1', '2021-04-21 00:32:35'),
(27, 82, 5, '1', '1', '2021-04-21 00:34:54'),
(28, 83, 2, '0', '1', '2021-04-21 00:42:59'),
(29, 84, 2, '0', '1', '2021-04-21 00:43:31'),
(30, 85, 2, '0', '1', '2021-04-22 00:54:25'),
(31, 86, 4, '0', '1', '2021-04-22 09:45:29'),
(32, 87, 1, '1', '1', '2021-04-22 12:44:05'),
(33, 88, 2, '0', '1', '2021-04-23 12:06:31'),
(34, 89, 2, '0', '1', '2021-04-23 12:07:15'),
(35, 90, 5, '0', '1', '2021-04-24 02:02:28'),
(36, 91, 5, '0', '1', '2021-04-24 02:03:36'),
(37, 92, 2, '0', '1', '2021-04-24 02:04:05'),
(38, 93, 2, '0', '1', '2021-04-24 02:04:36'),
(39, 94, 5, '0', '1', '2021-04-24 15:50:36'),
(40, 96, 2, '0', '1', '2021-04-25 21:59:36'),
(41, 97, 2, '0', '1', '2021-04-25 22:00:23'),
(42, 98, 5, '0', '1', '2021-04-26 21:25:46'),
(43, 99, 2, '0', '1', '2021-04-27 02:23:42'),
(44, 100, 2, '0', '1', '2021-04-27 02:24:06'),
(45, 101, 2, '0', '1', '2021-04-27 02:24:22'),
(46, 102, 2, '0', '1', '2021-05-01 02:09:11'),
(47, 103, 2, '0', '1', '2021-05-01 02:09:51'),
(48, 104, 5, '0', '1', '2021-05-01 16:58:02'),
(49, 105, 5, '0', '1', '2021-05-01 16:58:22'),
(50, 106, 2, '0', '1', '2021-05-01 17:19:12'),
(51, 107, 2, '0', '1', '2021-05-01 17:19:49'),
(52, 108, 5, '0', '1', '2021-05-02 02:03:56'),
(53, 109, 5, '0', '1', '2021-05-02 02:04:15'),
(54, 110, 2, '0', '1', '2021-05-02 11:35:53'),
(55, 111, 2, '0', '1', '2021-05-03 00:09:14'),
(56, 112, 5, '0', '1', '2021-05-03 16:34:03'),
(57, 113, 5, '0', '1', '2021-05-03 21:26:05'),
(58, 114, 5, '0', '1', '2021-05-03 21:26:31'),
(59, 116, 1, '1', '1', '2021-05-04 12:08:14'),
(60, 117, 5, '0', '1', '2021-05-04 19:41:26'),
(61, 118, 2, '0', '1', '2021-05-04 19:42:16'),
(62, 119, 2, '0', '1', '2021-05-04 20:23:30'),
(63, 120, 2, '0', '1', '2021-05-05 00:23:12'),
(64, 121, 2, '0', '1', '2021-05-05 00:23:25'),
(65, 122, 5, '0', '1', '2021-05-05 15:27:15'),
(66, 123, 2, '0', '1', '2021-05-08 11:57:48'),
(67, 124, 2, '0', '1', '2021-05-08 11:58:42'),
(68, 125, 2, '0', '1', '2021-05-08 12:10:10'),
(69, 126, 2, '0', '1', '2021-05-08 16:30:12'),
(70, 127, 5, '0', '1', '2021-05-08 16:33:14'),
(71, 128, 2, '0', '1', '2021-05-08 16:33:50'),
(72, 129, 5, '0', '1', '2021-05-08 16:34:16'),
(73, 130, 2, '0', '1', '2021-05-09 18:30:36'),
(74, 131, 2, '0', '1', '2021-05-09 18:31:01'),
(75, 132, 5, '0', '1', '2021-05-09 19:36:55'),
(76, 133, 5, '0', '1', '2021-05-10 19:47:03'),
(77, 134, 5, '0', '1', '2021-05-18 17:19:44'),
(78, 135, 5, '0', '1', '2021-05-18 17:20:03'),
(79, 136, 2, '0', '1', '2021-05-22 02:18:03'),
(80, 137, 2, '0', '1', '2021-05-22 02:18:27'),
(81, 138, 2, '0', '1', '2021-05-22 02:18:36'),
(82, 139, 2, '0', '1', '2021-05-31 04:11:53'),
(83, 140, 2, '0', '1', '2021-05-31 04:12:12'),
(84, 141, 1, '1', '1', '2021-06-02 16:04:00'),
(85, 142, 1, '1', '1', '2021-06-02 16:04:51'),
(86, 144, 1, '1', '1', '2021-06-02 16:34:44'),
(87, 145, 1, '1', '1', '2021-06-02 16:38:08'),
(88, 146, 1, '1', '1', '2021-06-05 00:27:00'),
(89, 147, 1, '1', '1', '2021-06-05 00:30:52'),
(90, 149, 1, '1', '1', '2021-06-05 17:43:45'),
(91, 151, 1, '1', '1', '2021-06-07 00:59:21'),
(92, 152, 1, '1', '1', '2021-06-07 01:03:11'),
(93, 153, 1, '1', '1', '2021-06-07 01:04:19'),
(94, 154, 1, '1', '1', '2021-06-08 00:52:28'),
(95, 155, 1, '1', '1', '2021-06-09 02:19:25'),
(96, 156, 1, '1', '1', '2021-06-09 02:21:02'),
(97, 160, 1, '1', '1', '2021-06-09 02:22:22'),
(98, 161, 1, '1', '1', '2021-06-09 18:49:07'),
(99, 162, 1, '1', '1', '2021-06-10 00:53:35'),
(100, 165, 1, '1', '1', '2021-06-12 14:36:02'),
(101, 166, 1, '1', '1', '2021-06-16 19:16:39'),
(102, 167, 1, '1', '1', '2021-06-17 21:21:18'),
(103, 169, 1, '1', '1', '2021-07-03 00:38:39'),
(104, 170, 1, '1', '1', '2021-07-04 16:08:58'),
(105, 172, 1, '1', '1', '2021-07-04 16:15:09'),
(106, 173, 1, '1', '1', '2021-07-05 19:13:22'),
(107, 174, 1, '1', '1', '2021-07-05 19:16:25'),
(108, 175, 1, '1', '1', '2021-07-05 19:22:31'),
(109, 176, 1, '1', '1', '2021-07-05 19:25:39'),
(110, 177, 1, '1', '1', '2021-07-05 19:38:45'),
(111, 178, 1, '1', '1', '2021-07-05 19:38:58'),
(112, 179, 1, '1', '1', '2021-07-19 01:12:22');

-- --------------------------------------------------------

--
-- Table structure for table `cancel_reason_driver`
--

CREATE TABLE `cancel_reason_driver` (
  `id` int(11) NOT NULL,
  `cancel_reason` varchar(255) NOT NULL,
  `type` enum('user','driver') NOT NULL,
  `status` enum('1','0') NOT NULL DEFAULT '1' COMMENT '1-active, 0-deactive',
  `added_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `cancel_reason_driver`
--

INSERT INTO `cancel_reason_driver` (`id`, `cancel_reason`, `type`, `status`, `added_on`) VALUES
(1, 'ffffffffff', 'user', '1', '2020-05-26 11:40:09'),
(2, 'ffffffffff', 'user', '1', '2020-05-26 11:40:27'),
(3, 'ffffffffff', 'user', '1', '2020-05-26 11:40:36');

-- --------------------------------------------------------

--
-- Table structure for table `cancel_reason_user`
--

CREATE TABLE `cancel_reason_user` (
  `id` int(11) NOT NULL,
  `cancel_reason` varchar(255) NOT NULL,
  `type` enum('user','driver') NOT NULL,
  `status` enum('1','0') NOT NULL DEFAULT '1' COMMENT '1-active, 0-deactive',
  `added_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `ci_sessions`
--

CREATE TABLE `ci_sessions` (
  `session_id` varchar(40) NOT NULL DEFAULT '0',
  `ip_address` varchar(45) NOT NULL DEFAULT '0',
  `user_agent` varchar(120) NOT NULL,
  `last_activity` int(10) UNSIGNED NOT NULL DEFAULT '0',
  `user_data` text NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `driver`
--

CREATE TABLE `driver` (
  `id` int(11) NOT NULL,
  `driver_id` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `img` varchar(255) DEFAULT NULL,
  `social_secrityno` varchar(255) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `zipcode` varchar(255) DEFAULT NULL,
  `address1` varchar(255) DEFAULT NULL,
  `address2` varchar(255) DEFAULT NULL,
  `referal_code` varchar(255) DEFAULT NULL,
  `latitude` varchar(255) NOT NULL DEFAULT '0.0000',
  `longitude` varchar(255) NOT NULL DEFAULT '0.0000',
  `token` varchar(255) DEFAULT NULL,
  `appPlatform` varchar(255) NOT NULL,
  `is_available` enum('1','0') NOT NULL DEFAULT '1' COMMENT '1 - available, 0 - Booked',
  `gender` enum('1','0') NOT NULL DEFAULT '1' COMMENT '1 - male, 0 - female',
  `driving_licence` varchar(255) DEFAULT NULL,
  `driving_licence_name` varchar(255) DEFAULT NULL,
  `driving_licence_code` varchar(255) DEFAULT NULL,
  `licence_plate` varchar(255) DEFAULT NULL,
  `vechile_reg` varchar(255) DEFAULT NULL,
  `expiredate` date DEFAULT NULL,
  `insuarance_img` varchar(255) DEFAULT NULL,
  `licence_img` varchar(255) DEFAULT NULL,
  `adhar_img` varchar(255) DEFAULT NULL,
  `vechile_img` varchar(255) DEFAULT NULL,
  `police_verification` varchar(255) NOT NULL,
  `is_online` enum('1','0') NOT NULL DEFAULT '0' COMMENT '1 - online, 0 - offline ',
  `driving_licence_status` enum('0','1','2') NOT NULL DEFAULT '0' COMMENT '0 - pendding, 1 - verified, 2 - rejected',
  `police_verification_status` enum('0','1','2') NOT NULL DEFAULT '0' COMMENT '0 - pendding, 1 - verified, 2 - rejected',
  `driving_licence_rejection_reason` varchar(255) DEFAULT NULL,
  `police_verification_rejection_reason` varchar(255) DEFAULT NULL,
  `status` enum('1','0') NOT NULL DEFAULT '1' COMMENT '1 - active, 0 - deactive',
  `added_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `driver`
--

INSERT INTO `driver` (`id`, `driver_id`, `email`, `first_name`, `middle_name`, `last_name`, `password`, `mobile`, `country`, `city`, `state`, `img`, `social_secrityno`, `dob`, `zipcode`, `address1`, `address2`, `referal_code`, `latitude`, `longitude`, `token`, `appPlatform`, `is_available`, `gender`, `driving_licence`, `driving_licence_name`, `driving_licence_code`, `licence_plate`, `vechile_reg`, `expiredate`, `insuarance_img`, `licence_img`, `adhar_img`, `vechile_img`, `police_verification`, `is_online`, `driving_licence_status`, `police_verification_status`, `driving_licence_rejection_reason`, `police_verification_rejection_reason`, `status`, `added_on`) VALUES
(1, 'QJYE476J', 'woso8@hotmail. com', 'Wally', NULL, 'Smith', 'athlone8', '+18632589252', 'United States', 'Orlando', 'fl', '', '12346777', '1970-01-01', '34711', '1 test st', 'orlando', NULL, '28.7078857421875', '-81.39023113451388', 'cMv1Ojn9MHg:APA91bG60taHW-OGmi2BVAyJH2VMw58VYVi4svMZYSaXxyzMnalLZYjgbmRujh6Uy1uiSIe6vmNPwfUWx6T2F4ioHDQJxlkpDjlJY0Z0tpyYq_SW5EtIRNWI5pr3Izrb2jBToimQLJwm', 'ios', '1', '1', '123566778', NULL, NULL, 'chhjkkk', 'rthhhnnm', NULL, '0debe8ee36c23981558c77711a35603b.jpg', 'e0f4934ade866453f981dfcd1f161230.jpg', '9c259fc9a03de88ea655de87a419883e.jpg', 'dc640e04df9518d377ce6c75d88bf458.jpg', '', '1', '0', '0', NULL, NULL, '1', '2021-07-22 17:28:36'),
(2, 'LILMGPA0', 'nnn', 'nnn', NULL, 'nnn', 'nnn', '+918878870029', 'India', 'mh', NULL, '', NULL, NULL, NULL, NULL, NULL, '', '23.2313', '77.43259999999999', '93283232932098231902130843980jndsjn0208', 'ios', '1', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '1', '0', '0', NULL, NULL, '1', '2021-08-05 06:18:58'),
(3, 'V4MNR4ES', 'allen@gmail.com', 'allen', NULL, 'walker', '123', '+918839074576', 'India', 'MH', NULL, '', NULL, NULL, NULL, NULL, NULL, '', '23.2313', '77.43259999999999', '93283232932098231902130843980jndsjn0208', 'ios', '1', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '0', '0', '0', NULL, NULL, '1', '2021-08-05 06:21:34'),
(4, '753EZWG7', 'skywinx08@gmail.com', 'sky', NULL, 'winx', '1234', '+918109601343', 'India', 'bhopal', NULL, '', NULL, NULL, NULL, NULL, NULL, '', '23.2313', '77.43259999999999', 'e5dcnyL9mCo:APA91bFhVkms_xo5p8yUXeqa5N1l6s5D5HCD79u3RIYZwW_kV61NoBEshwcrlrbSpibUsswRqdMAj-0wEmoc_ruYzdGlkpBE6f3RF-_tsJvpXpY7LpeOBI5Q_Nr8G1BYr0WBCEg5_01F', 'ios', '1', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '0', '0', '0', NULL, NULL, '1', '2021-12-06 11:59:08'),
(5, '2GAQJWXY', 'skysky@gmail.com', 'skyyyyyy', NULL, 'dkdjdj', '123123', '+919399517232', 'India', 'india', 'ca', '', '123123', '1970-01-01', 'Jebrat al Sheikh, Sudan', 'djdjd', 'skdjd', '', '23.22868347167969', '77.43489576607188', 'e5dcnyL9mCo:APA91bFhVkms_xo5p8yUXeqa5N1l6s5D5HCD79u3RIYZwW_kV61NoBEshwcrlrbSpibUsswRqdMAj-0wEmoc_ruYzdGlkpBE6f3RF-_tsJvpXpY7LpeOBI5Q_Nr8G1BYr0WBCEg5_01F', 'ios', '1', '1', '283ekdej', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '1', '0', '0', NULL, NULL, '1', '2021-11-11 09:55:46');

-- --------------------------------------------------------

--
-- Table structure for table `driver_vechile`
--

CREATE TABLE `driver_vechile` (
  `id` int(11) NOT NULL,
  `driverid` int(11) NOT NULL,
  `vechile_type` varchar(222) NOT NULL,
  `vechile_subtype` varchar(222) NOT NULL,
  `model` varchar(222) NOT NULL,
  `color` varchar(222) NOT NULL,
  `make` varchar(255) NOT NULL,
  `year` varchar(255) NOT NULL,
  `noofdoor` varchar(255) NOT NULL,
  `noofsbelt` varchar(255) NOT NULL,
  `addedon` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `driver_vechile`
--

INSERT INTO `driver_vechile` (`id`, `driverid`, `vechile_type`, `vechile_subtype`, `model`, `color`, `make`, `year`, `noofdoor`, `noofsbelt`, `addedon`) VALUES
(1, 1, '1', '1', '1', '1', '2007', '1991', '5', '1', '2021-07-05 18:50:55'),
(2, 5, '1', '1', '1', '1', '1942', '1991', '5', '1', '2021-10-28 13:12:24'),
(3, 19, '1', '1', '1', '1', '2007', '1991', '5', '1', '2021-04-19 06:03:46'),
(4, 4, '1', '1', '1', '1', 'dgfd', '1991', '5', '1', '2021-08-26 08:51:56'),
(5, 2, '1', '1', '1', '1', '2007', '1991', '5', '1', '2021-07-12 10:55:10'),
(6, 3, '1', '1', '1', '1', '2007', '1991', '5', '1', '2021-08-05 11:34:55');

-- --------------------------------------------------------

--
-- Table structure for table `driver_vechileimg`
--

CREATE TABLE `driver_vechileimg` (
  `id` int(11) NOT NULL,
  `driverid` int(11) NOT NULL,
  `img` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `driver_vechileimg`
--

INSERT INTO `driver_vechileimg` (`id`, `driverid`, `img`) VALUES
(1, 1, 'image34.jpeg');

-- --------------------------------------------------------

--
-- Table structure for table `email_template`
--

CREATE TABLE `email_template` (
  `id` int(11) NOT NULL,
  `template_name` varchar(255) NOT NULL,
  `template_subject` varchar(255) NOT NULL,
  `template_description` text NOT NULL,
  `status` enum('1','0') NOT NULL DEFAULT '1' COMMENT '1 - active, 0 - deactive',
  `added_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `faq`
--

CREATE TABLE `faq` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` longtext NOT NULL,
  `status` enum('1','0') NOT NULL DEFAULT '1' COMMENT '1 - Active, 0 - Deactive',
  `added_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `faq`
--

INSERT INTO `faq` (`id`, `title`, `description`, `status`, `added_on`) VALUES
(6, 'Terms & Conditions', '1. Introduction\r\n1.1. Welcome! The Website www.Arrive5.com (the “Site”) and our mobile software application (the “Arrive5 App”) are operated by Arrive5 Technology Inc., located at 14900 East Orange Lake Blvd., #332, Kissimmee, FL 74747 (the “Company, We, Our or Us”). The Arrive5 App helps people who download the Arrive5 App and agree to the within Terms and Conditions (together, “Rider, Riders, You or Your”) find and hire drivers for passenger transport (the “Services”). These Terms and Conditions, which summarize and incorporate by reference our Privacy Policy, explain the contractual relationship between You and Company regarding your use of, and access to the Arrive5 App, and Services, and constitute a binding legal agreement between You and the Company. By downloading the Arrive5 App and accepting these terms and conditions,users consent to the sharing of their data for use by Arrive5. \r\n1.2. PLEASE READ THESE TERMS AND CONDITIONS CAREFULLY AND IN THEIR ENTIRETY BEFORE USING THE ARRIVE5 APP AND/OR THE SERVICES, AND CHECK THEM PERIODICALLY FOR CHANGES. YOUR USE OF THE SITE IS SUBJECT TO AND CONDITIONED UPON YOUR AGREEMENT TO, AND COMPLIANCE WITH, ALL OF THE TERMS AND CONDITIONS BELOW (THE “AGREEMENT”). IF YOU DO NOT AGREE TO THESE TERMS AND CONDITIONS, YOU MAY NOT USE THE ARRIVE5 APP. BY COMPLETING THE REGISTRATION PROCESS, ACCESSING AND USING THE ARRIVE5 APP, AND USING THE SERVICES, YOU ARE INDICATING THAT YOU ARE 18 YEARS OF AGE OR OLDER, THAT YOU HAVE READ, UNDERSTAND AND UNCONDITIONALLY ACCEPT AND AGREE TO BE BOUND BY THESE TERMS AND CONDITIONS WHICH APPLY TO ALL SERVICES RENDERED WITHIN THE USA.\r\n2. Accepting these Terms and conditions\r\n2.1. Your use of the Arrive5 App, or any other Company software licensed through a third party distributor is also governed by any applicable current end user license agreement (“EULA”) made available through the third party distributor. In the event of a conflict between any provision in these Terms and Conditions and any EULA, the conflicting provision in these Terms and conditions will prevail. \r\n2.2. Certain offerings, including without limitation sweepstakes, rewards, contests of skill, or promotions on the Site or through the Arrive5 App, may be subject to additional guidelines, rules, or terms (“Additional Terms“). These Additional Terms will be located where We post the sweepstakes, rewards, contests of skill, or promotions on the Site. In the event of a conflict between the Terms and Conditions and the Additional Terms, the Additional Terms take precedence in relation to that Service. The Additional Terms for such Services are hereby incorporated by reference into these Terms and Conditions. \r\n2.3. We may in Our sole discretion and at any time, change, add, or delete portions of these Terms and Conditions and/or any other terms on the Site. We will notify You of any such material changes. Your continued use of the Site following such notice constitutes Your acknowledgment and acceptance of, and agreement to, such changes. \r\n2.4. You acknowledge that these Terms and Conditions are supported by reasonable and valuable consideration, the receipt and adequacy of which You hereby acknowledge, and which include, but are not limited to, Your use of the Arrive5 App, and receipt of data, materials and information available at or through the Arrive5 App, the possibility of Our use or display of Your Submissions (as defined below in Section 15), and the possibility of the publicity and promotion from Our use or display of Your Submissions. \r\n3. Use of the Arrive5 App\r\n3.1 Riders must create an Account to access the Services (“Account”). Riders shall access and use the Arrive5 App only for personal purposes, and only as long as they are in compliance with all provisions of the Agreement. Riders shall not use the Arrive5 App in any way that violates the law or the terms of this Agreement, or harms Us, or any other person or entity, as determined in Our sole discretion. We reserve the right to requires Riders who use of the Arrive5 App for commercial purposes to create a business account. \r\n3.2 You may close Your Account and cease to be a Rider at any time by sending Us written notification via email to help@Arrive5.com, or by mail to our address at 14900 East Orange Lake Blvd., #332, Kissimmee, FL 74747 If your Account is closed or suspended, either by You or by Us, same shall not relieve You of any payment obligations that You incurred in connection with the Services while the Account was active. \r\n3.3 We or third parties authorized by Us may from time to time place commercial content on the Arrive5 App. We have no control over for the accuracy, truthfulness, quality, safety or legal aspects of content provided by third parties, nor do We endorse, guarantee, or are We responsible for such content (even if Riders receive any benefits related to the Arrive5 App in connection with such third party offers). It is Rider’s responsibility to understand and accept the terms and payment obligations of all such content that Riders pursue. \r\n3.4 Occasionally, the Arrive5 App may experience interrupted service, delays or errors. This may be due to a number of reasons including maintenance that We perform on the Arrive5 App, as well as reasons beyond Our control. We will attempt to provide You with prior notice of any interruptions, delays or errors, but cannot guarantee that such notice will be provided. Should You experience any such issues, please contact us at support@Arrive5.com so that We may address the issue. \r\n3.5 Minimum hardware and software requirements for use of the Arrive5 App may be posted from time to time. However, We do not guarantee the access to or performance of the Arrive5 App even if Riders meet such minimum requirements. \r\n3.6 We may, in Our sole discretion and at any time, in any way, for any reason, change or discontinue any part of the Arrive5 App or Services with or without notice to You. We may, in the future and in Our discretion, impose charges for accessing any or all portions of the Arrive5 App or Services, in which event We will provide notice before such access is granted and charges incurred. We shall not be liable to You or any third party should We exercise Our right to modify or discontinue the Arrive5 App or Services. If You object to any such changes, Your sole recourse will be to cease accessing the Arrive5 App or Services. Your continued access of the Arrive5 App or Services following any such changes shall constitute Your acknowledgement of and agreement to such changes, and Your satisfaction with the Arrive5 App or Services as so modified. You agree that We may in Our sole discretion and at any time, in any way, for any reason, immediately terminate Your access to the Arrive5 App and Services. YOU ACKNOWLEDGE AND AGREE THAT WE SHALL NOT BE LIABLE TO YOU OR ANY OTHER PARTY FOR ANY TERMINATION OF YOUR ACCESS TO THE ARRIVE5 APP OR SERVICES. TERMINATION OF YOUR ACCOUNT SHALL IN NO WAY MODIFY OR VOID ANY PAYMENT OBLIGATIONS YOU MAY HAVE INCURRED THROUGH YOUR USE OF THE ARRIVE5 APP OR SERVICES, WHETHER SUCH OBLIGATION IS TO US OR A THIRD PARTY.\r\n4. Accounts\r\n4.1 To set up an Account, You must provide, among other things, your name, a functioning mobile phone number and e-mail address where You can be reached, a password of your selection which You agree not to transfer to or share with any third parties, and a method of payment (collectively, Your “Account Information“). The mobile telephone number must be SMS-capable so that You may receive temporary verification codes from Us for purposes of verifying Your identity and keeping Your Account secure. You agree to provide true, accurate, current and complete information for Your Account. You shall not (a) register for more than one Account, (b) register for an Account on behalf of a third party, (c) use any Account other than Your Account, or (d) permit any third party to use Your Account. \r\n4.2 You are solely responsible for all use of Your Account Information and activities, act or omission of any third party, including without limitation any act or omission in violation of these Terms and Conditions, that may occur under or in connection with Your Account. You shall notify Us immediately by sending an email to help@Arrive5.com or support@Arrive5.com if you become aware that Your Account Information is being used without Your authorization. \r\n4.3 We reserve the right to take any and all action as deemed necessary or reasonable in Our sole discretion, to ensure the security of the Arrive5 App and Your Account, including without limitation terminating Your Account, changing Your password, or requesting additional information to authorize transactions on Your Account. \r\n5. Services and Pricing\r\n5.1. We offer multiple types of service. While we do not charge fees to use the Arrive5 App, We may charge for certain auxiliary services and options. Complete pricing information is presented to You through the Arrive5 App (such pricing information hereinafter referred to as a “Fee Estimate”). Any Fee Estimate presented to You via the Arrive5 App is valid for a specified period of time, and is based upon time and distance factors, high demand surcharges, and other factors such as traffic conditions prevailing at the time the Fee Estimate was quoted. Fee Estimates for identical itineraries quoted at different times may vary due to these factors. Additional fees not included in the Fee Estimate, including without limitation fees for wait time, tolls, surcharges, additional stops, changes to final destination, other additional charges, variations in ground conditions, and any optional gratuities you may extend to the driver may result in an actual fee (“Actual Fee”) charged to You that is different than the fee initially quoted to You. You shall be fully liable for payment of the Actual Fee each time You reserve or receive Services. \r\n5.2. The pricing methodology used to calculate the Fee Estimate may differ from that used to calculate the fee paid to the driver. For example, as is noted above, time and distance factors as well as other factors such as prevailing traffic conditions are used to calculate the Fee Estimate that You are quoted. Subsequent changes in such factors (an accident, gridlock, etc.) that occur after the Fee Estimate is given may require the driver to take a longer and/or less direct route to deliver You to your destination in a timely manner. Under such circumstances We honor the Fee Estimate and pay the driver based upon the longer route he or she was required to take. The Fee Estimate is not honored if the ride takes significantly longer in duration or distance due to changes You make such as requesting an additional stop or changing your destination. Under such circumstances You will be charged the Actual Fee reflecting the cost of the ride based upon the minimum fare, the distance, and the duration of the ride. \r\n5.3. A third party navigation service is used to determine a recommended route to be taken by the driver, however the route ultimately taken is determined by the driver in his or her discretion based upon prevailing traffic and other conditions. We assume no responsibility for the efficiency of the route the driver takes and/or for any unexpected delays and/or related charges that may occur, irrespective of whether the route is one selected by the navigation service, the driver, or You, and irrespective of whether the driver refuses to take an alternate route You may request. \r\n5.4. Gratuities are optional, and any gratuity you extend to a driver will be passed through by Us in full and without deduction. Processing fees may apply. \r\n5.5. From time to time, We may beta test new services and invite You to participate. If You are invited to participate and agree to do so, You may be asked to agree to additional Terms and Conditions. \r\n6. Reservations; Service\r\n6.1. These Terms and Conditions, together with the Additional Terms and any other terms on the Site shall govern any request for Services You submit through the Arrive5 App (a “Reservation“). \r\n6.2. You understand and acknowledge that all Reservations for passenger transport made via the Arrive5 App are being made with third party transportation providers, and that you will be picked up in a For Hire Vehicle that is owned by and/or affiliated with such third party transportation provider. \r\n6.3. For your safety, you should verify that the driver that arrives to pick you up matches the driver photo displayed in the App. If a different driver arrives, do not get in the vehicle instead, contact customer support immediately at help@Arrive5.com. \r\n6.4. In the event We or the applicable Transportation Provider cannot fulfill the Reservation, We will use commercially reasonable efforts to contact You via the contact information associated with Your Account. We shall not be liable or responsible to You in the event we cannot accept or fulfill a Reservation. \r\n6.5. You acknowledge and agree that any violation of the Terms and Conditions, the Additional Terms or any other terms may result in cancellation of Your Account and/or Your Reservation(s), in Your being denied access to any Reservation, and/or in Your forfeiting any fees You may have paid for such Reservation(s). You hereby authorize Us to debit Your Account (via the method of payment associated with Your Account) for any costs We incur as a result of such violation. \r\n6.6. You authorize Us to charge Your Account for any additional costs and/or services incurred by You in connection with Your use of the Services, including without limitation Damage Charges as defined infra, and additional services you may request the driver perform, such as returning items left behind in the driver’s vehicle. \r\n6.7. Any information related to the times for Services (including arrival time of a Transportation Provider and how long a delivery or passenger trip will take) are estimates only (“Time Estimates”) and provided for Your convenience. Time Estimates shall not constitute a guaranteed or comprehensive time or price quote and You shall not rely on Time Estimates. The actual arrival time and length of Services are subject to factors outside of Our control, including without limitation weather, traffic, ground conditions and the actions of the Transportation Provider. We shall not be liable, and You hereby waive any claim, cause of action, damages, demands or liability against Us, arising from a failure by a Transportation Provider or its driver either to pick you up or deliver you to your intended destination in a timely manner, or to timely deliver any goods. \r\n6.8. Minors; Car Seats. You must be an adult eighteen (18) years of age or older in order to maintain an account with Us and order service. Children and infants are welcome to travel with You in the back seat of the vehicle, however We do not permit minors to ride in the front passenger seat and/or to ride unaccompanied by an adult. We reserve the right to request proof of age from passengers who appear to be minors and who are not accompanied by an adult, to cancel any trip request made by or on behalf of an unaccompanied minor, to require a minor to leave the vehicle together with the adult account holder should the adult account holder do so before reaching the destination, and/or to close any account opened by a minor or used to order service on behalf of an unaccompanied minor in violation of Our policies. If traveling with a child or infant, you remain responsible at all times for ensuring that the child/infant is properly restrained by means of an approved restraint system appropriate for the child/infant’s age and weight (i.e. a lap/shoulder seat belt, booster seat and lap/shoulder seat belt combination, or car seat) prior to departure, and at all times during the ride. You shall also remain responsible at all times for ensuring that any child that accompanies You does not interfere in any manner with the driver and/or with the safe operation of the vehicle. You shall be responsible for providing a booster seat or car seat appropriate for the age and weight of each child/infant that accompanies you. You shall be solely responsible for installing such car seats, ensuring that they are properly placed within the car, ensuring that any user of the car seat is properly buckled in the car seat, and ensuring that use of the car seat complies with all applicable federal, state and local laws. You hereby waive and release Us from any claims, causes of action, damages or other liabilities, including without limitation those causing damage to person or property, or death, that result either from defects in the manufacture of the car seats, malfunction of the car seats or any parts thereof, failure to properly install the car seat, failure to properly buckle and restrain the child or infant, or any use or misuse of the car seats. \r\n6.9. Damage to Vehicle. In the event You or passengers traveling with You soil and/or cause damage to the Driver’s vehicle, You shall be responsible for and hereby agree to pay for the cost of cleaning and/or repair (the “Damage Charge”). The Damage Charge will be billed to Your account and following collection paid in full to the vehicle owner, and You hereby authorize Us to debit Your Account (via the method of payment associated with Your Account) for any Damage Charges incurred. Should you fail to pay the Damage Charge, the vehicle owner shall be entitled to pursue legal claims against You. \r\n7. Payment, Receipts and Refunds\r\n7.1. Payment for Services shall be made through the Arrive5 App. To pay for Services through the Arrive5 App, You shall provide Company with the payment information necessary to process a Reservation prior to making the Reservation. Your submission of Your payment information to Us constitutes Your authorization to Us to charge a temporary pre-authorization hold prior to your ride, and thereafter to charge the applicable fees. You represent that You will not use any credit card, debit card or other form of payment unless You have all necessary authorization to do so. If a credit card charge or other form of payment is subsequently dishonored, same shall not relieve You of Your obligation to pay for the services You have received. \r\n7.2. At the conclusion of your ride, you will sent a receipt via e-mail. We reserve the right to issue amended receipts in the event the amount appearing on the original receipt is inaccurate. \r\n7.3. We reserve the right to issue refunds in our discretion. All refunds shall be in the form of Account credit only. We do not issue cash refunds or refunds of any kind other than Account credit. \r\n7.4. Some banks and credit card companies impose fees for certain transactions. Such fees are determined solely by Your bank or credit card company and We are not responsible for payment of any such fees. If You have any questions about these fees or the exchange rate applied to Your Reservation, please contact Your bank or credit card company. \r\n7.5. Credit cards/Debit Cards do not include pre-paid cards, one-time cards, gift cards, virtual cards, anonymous cards and reloadable cards. \r\n7.6. You agree to pay a late fee equal to 10% for all balances that remain unpaid 90 days after the date of service. If We are required to refer Your account to collection (including, but not limited to referral to a collection agency or attorney), You agree that in addition to the charges and late fees which may be due, You will reimburse Us for collection costs of 25% of the unpaid balance, including late fees. If Your account is subject to any discount, deal or promotion, You will lose the benefit of the discount, deal and/or promotion on the 91st day after the date of service if payment is not received by the 90th day. The discount, deal and/or promotion will be reinstated in Our discretion, and only after Your account balance has been paid in full. We reserve the right to discontinue service to You in the event you maintain an open balance on your account that is more than 90 days old. \r\n8. Account Credits and Promotions\r\n8.1. From time to time, We may offer promotions, deals or discount codes for the Arrive5 App. We may in Our sole discretion establish, modify, suspend, end, reject or refuse to honor such promotions, deals or discount codes at any time, with or without notice to You. We reserve the right to set limitations on the use of promotions, deals and/or discount codes. Opening multiple accounts on the same device for purposes of redeeming a promotion, deal and/or discount code more than once is prohibited and shall result in the forfeiture of all promotional credits and/or suspension of the accounts. Additional terms regarding promotions, deals or discount codes will be posted on the Site or in the Arrive5 App. \r\n8.2. Promotional credits and customer care credits (“Account Credits”) may be applied to the immediately subsequent rides You take with Us until used in full. Account Credits are non-transferrable, may not be redeemed for cash, and expire one year from the date of issuance. \r\n9. Cancellations and No-Shows\r\n9.1. Any cancellations, no-shows or failure to be present to accept deliveries shall be governed by Our current cancellation policy, which may be found in our in-app Help Center. Additionally, certain Reservations shall have specific cancellation windows and You shall be responsible for all fees if You fail to show for a Reservation and fail to cancel the Reservation within the cancellation window. \r\n10. Lost Property.\r\n10.1. We are not responsible for any personal property left behind in a driver’s vehicle. If you lose or leave something behind, please contact our customer support team at help@Arrive5.com so that they may put you in touch with your driver directly. \r\n11. Mobile Communications\r\n11.1. By accessing and using the Arrive5 App or Services with a mobile device, You acknowledge and agree that You may receive certain communications from the Arrive5 App including without limitation SMS, MMS, text messages, mobile emails, push notifications, or other electronic communications means (collectively “Mobile Communications”). By accessing and using the Arrive5 App or Services via mobile devices, or by using certain mobile features including without limitation sending or receiving Mobile Communications, You may incur fees from the provider or carrier of the mobile services that You use (“Carrier“) and You shall be solely responsible for the payment of such fees. \r\n11.2. If You elect to include information about Your location (including location-related information provided by Your Carrier or any applications) in Your Account Information, You acknowledge, accept and agree that (i) such information shall be made available to Transportation Providers when You make a Reservation for Services; and (ii) Company shall not be responsible for the accuracy of such information or any use of such information by third parties including without limitation Transportation Providers. \r\n12. Electronic Communications\r\n12.1. You (i) consent to receive communications from Us in an electronic form (including via phone call, e-mails and text message), including without limitation marketing and promotional Communications, advertisements and telemarketing messages; and (ii) agree that all Terms and Conditions, agreements, notices, documents, disclosures, and other communications (“Communications“) that We provide to You electronically satisfy any legal requirement that such Communications would satisfy if it were in writing. Your consent to receive Communications and do business electronically, and Our agreement to do so, applies to all of Your communications and transactions with Us. The foregoing does not affect Your non-waivable rights. You may withdraw your consent to receive marketing and promotional Communications, advertisements and telemarketing messages by contacting Us via email at help@Arrive5.com. The withdrawal of Your consent will not affect the legal validity and enforceability of any obligations or any electronic Communications provided by Us or any business transacted prior to the time You withdraw Your consent. You shall keep Us informed of any changes in your email address so that You continue to receive all Communications without interruption. \r\n13. Proprietary Rights\r\n13.1. The design of the Arrive5 App, and all content, services, features, data, text, artwork, images, photographs, graphics, drawings, videos, audiovisual works, scripts, logos, copyrights, trademarks, service marks, patents, slogans, trade names, trade secrets, trade dress, format, design, Rider interface, software, information, functions, computer games, dialogue, ideas, concepts, suggestions, stories, screenplays, music, lyrics, sound recordings, profiles, appearances, performances, and/or other similar materials displayed on or that can be downloaded from the Arrive5 App, are protected by copyright, trademark and other laws and may not be used except as permitted in these Terms and Conditions or with prior written permission of the owner of such material. The contents of the App are © 2017, all rights reserved. You may not modify the information or materials displayed on or that can be downloaded from the Arrive5 App in any way or reproduce or publicly display, perform, or distribute or otherwise use any such information or materials for any public or commercial purpose. Any unauthorized use of any such information or materials may violate copyright laws, trademark laws, laws of privacy and publicity, and other laws and regulations. All material (collectively, the “Content”) appearing on or in the Arrive5 App, including but limited to the images, video, text, terms and conditions, privacy policy, source code, designs, or anything else that is delivered when you use the Arrive5 App, is protected intellectual property owned by Us. You may use the Content under and according to these Terms, but all other uses are prohibited and will be treated as an infringement of our intellectual property rights. You shall not: \r\n(ⅰ) sublicense, sell, assign or otherwise share the Content with anyone;\r\n(ⅱ) duplicate any part of the App or any Content appearing on the App, for any purpose (except as expressly provided elsewhere in these Terms); \r\n(ⅲ) distribute, share, trade or create any derivative works based on the Arrive5 App, or any of the Content, and you agree that any such use is NOT “fair use” under 17 U.S.C. § 107; \r\n(ⅳ) use the Arrive5 App and/or any of the Content for any public display, public performance, sale or rental, and you hereby agree and stipulate that any and all such uses are NOT “fair use” under 17 U.S.C. § 107; \r\n(ⅴ) post, share, trade or offer for use/viewing/listening to or transcription copy of any or all of  the Content to or through any websites or service, including, without limitation, through  one-click hosting sites, file locker sites, bit torrent protocol, public or private forums, social  sites, video hosting “tube” sites, or any other similar technology; \r\n(ⅵ) remove any copyright or other proprietary notices from any of the Content; or \r\n(ⅶ) circumvent any encryption or other security tool(s) used anywhere on the Arrive5App.You acknowledge and agree that the foregoing list of prohibited uses is exemplary, non-exhaustive, and provided for illustrative purposes only. You further agree that the use of bots or any kind of automated process to copy, download, hot-link, frame, or otherwise use any Content is prohibited and will in all instances be considered commercial uses. Any license granted to You will terminate and be immediately revoked upon Your use of any Content in violation of this Section. \r\n14. Submissions\r\n14.1. “Submissions” are any information, text, messages, concepts, suggestions, feedback, stories, screenplays, treatments, formats, artwork, photographs, videos, audiovisual works, musical compositions including lyrics, sound recordings, recordings, actions, appearances, performances Your or another persons’ name, likeness, voice, Username, profile, and/or other biographical information or material, and/or other similar materials that You email, post, upload, embed, display, publish, communicate or otherwise submit (collectively, “Submit”) on or through or to the Site. Submissions may be publicly displayed on Our site. You warrant and represent that You are the rightful owner of all of the rights to Your Submissions (including without limitation moral rights) or have the appropriate license or sublicense rights from the owner, without the need for any permission from or payment to any other person or entity, and that the information You Submit to the Site is true and accurate. You are entirely and solely responsible for all Your Submissions and the consequences of submitting them to, or posting or publishing them on, the Site. \r\n14.2. We do not accept, review or otherwise consider unsolicited Submissions and request that Riders do not submit any unsolicited Submissions. Riders acknowledge that there is no confidential or fiduciary relationship between Riders and Us whatsoever and that We will not review or offer any consideration or compensation for any Submissions. Riders hereby grant to Us and Our officers, directors, employees, agents, licensees, distributors, representatives and affiliates, a non-exclusive, perpetual, irrevocable, unrestricted, fully-paid, royalty-free, sub-licensable and transferable (in whole or in part) worldwide license for an indeterminate period (or for such maximum period permitted by applicable law) under all intellectual property rights You own or control to use, reproduce, distribute, transmit, prepare derivative works of, publicly display, index, comment on, modify, perform and otherwise exploit Your Submissions, in whole or in part, for any purpose and in any media formats and channels (including among others on other Websites, and in products and services offered by Us) now known or subsequently devised, in each case without compensation, attribution, liability or notice to You and without the requirement of any permission from or payment to You or to any others. If You request in writing that We remove Your Submissions from the Site, We will remove any public display of Your Submissions and the license granted by You to Us shall terminate (except that We shall not be required to change any materials used by Us that already include Your Submissions). \r\n14.3. No portion of Your Submission shall be subject to any obligation of confidence on Our part and You should expect no privacy with respect to Your Submissions, except for personally identifiable information (“PII”) that is subject to Our Privacy Policy and is not made publicly available by You. PII is information that is personal to a Rider and which can be used to identify or contact a Rider, including first and last name, home or other physical address including street name and city or town, email address, telephone number, Social Security number, or any other identifying information that would permit physical or online contact with a Rider. \r\n14.4. We reserve the right to decide, in Our sole discretion, whether, where, and how a Submission is published on the Site. If We have questions about Your Submissions, We may contact You for further information (including, but limited to, to verify that You own the copyright or other intellectual property rights). Although We have no obligation to review any Submissions, We reserve the right, in Our sole discretion, to edit or remove any and all Submissions, without prior notice. \r\n15. Rules of Conduct\r\n15.1. In connection with Your use of the Site, its Content, and any and all Submissions, You agree to abide by all applicable federal, state local, national and international laws and regulations and not, nor allow or facilitate others, to violate or infringe any rights (including, but not limited to, copyrights, rights of publicity or privacy, confidentiality and trademarks rights) of others, Our policies or the operational or security mechanisms of the Site. Without limiting the foregoing You shall NOT: \r\na)	Use the Site or Arrive5 App (including, but not limited to, in any Public Forums (as defined below) or other communication systems provided by the Site or Arrive5 App) or any of its Content, including, but not limited to, Your or other Riders’ Rider Submissions, to promote, conduct, or contribute to activities that in Our sole discretion are profane, defamatory, infringing, fraudulent, obscene, pornographic, indecent, commercial, inappropriate or illegal, including, but not limited to, pyramid schemes, surveys, contests, chain letters, gambling, junk e-mail, spamming, promoting hatred towards any group of people or conduct that can reasonably be expected to harm others. \r\nb)	Use the Site or Arrive5 App in any manner which could damage, disable, overburden, or impair the Site or Arrive5 App, or interfere with the access, use or enjoyment of this Site of Arrive5 App by others, including, but not limited to, causing greater demand on the Site or Arrive5 App than is deemed reasonable by Us, attacks such as “flaming” other Riders in a manner that might incite or perpetuate a conflict or argument, creating Usernames to attack other Riders’ identities. \r\nc)	Defame, abuse, harass, stalk, threaten or otherwise violate the legal rights (such as rights of privacy and publicity) of others. \r\nd)	Harvest, compile or otherwise collect PII about another Rider through the Site or Arrive5 App (including, but not limited to, email addresses). \r\ne)	Copy, modify, distribute, transmit, publicly display, publicly perform or create derivative works of any portion of the Site, Arrive5 App, or any of their Content. \r\nf)	Obtain or attempt to obtain any materials or information through any means not intentionally made available or provided for through the Site or the Arrive5 App. \r\ng)	Frame, hyper-link, or otherwise interfere with or in any manner disrupt, circumvent, overburden or compromise any part of the Site or Arrive5 App, their Content, or features that prevent or restrict use or copying of any Content or enforce limitations on use of the Site, Arrive5 App or Content. \r\nh)	Decompile, disassemble, reverse engineer or otherwise attempt to discover any source code or underlying ideas or algorithms of the Site, Arrive5 App or their Content except if and to the extent permitted by applicable law. \r\ni)	Use any robot, spider, other automated device or any tool-bar, Web-bar, other Web-client, device, software, routine or manual process, to monitor or scrape information from the Site or Arrive5 App, or bypass any robot exclusion request (on headers or anywhere else on the Site of the Arrive5 App). \r\nj)	Use any meta tags or any other “hidden text” utilizing any Content or intellectual property rights owned by, or licensed to, or by, Us. \r\nk)	Access or attempt to access any other of Our systems, programs or data that are not made available by Us for public Use. \r\nl)	Create or provide any other means through which the Site or Arrive5 App may be accessed, for example, through server emulators, whether for profit or not. \r\n15.2. We cannot and do not assure that other Riders comply with this Agreement, and Riders assume all risk of harm or injury resulting from any such lack of compliance. \r\n16. Public Communications and Forums\r\n16.1. The Site and the Arrive5 App may enable Riders to interact directly with other Riders, such as by sending public or pre-defined private messages and posting comments in chat rooms, bulletin board services, news groups, communities, personal Web pages, calendars, and /or other message or communication facilities and forums designed to enable Riders to communicate with the public at large or with other Riders (“Public Forums”). Use of the Public Forums is permitted only for noncommercial purposes and subject to the terms of the Agreement. You agree to use the Public Forums only to submit, send and receive messages and material that are proper and related to the purpose of this Site and the Arrive5 App. \r\n16.2. Riders are solely responsible for the Submissions they make, and the consequences thereof, on or through the Arrive5 App, the Site and its Public Forums. We do not endorse, guarantee, nor are We responsible for the information, opinions, or recommendations submitted by any Rider in any Public Forum or otherwise in connection with the Site or Arrive5 App and expressly disclaim any all liability in connection therewith. \r\n16.3. Although We are investing efforts in ensuring a safe and pleasant environment, when using the Site or the Arrive5 App You may be exposed to Submissions by others (a) with which You may disagree, (b) that You may find offensive, indecent, or objectionable, or (c) which are inaccurate, misleading or illegal. You expressly assume and agree to bear any and all risks associated with Your use of, exposure to or reliance on any such Submissions. You should be skeptical about information provided by others, and You acknowledge that there is a possibility of use of any Submissions by others, and that Submissions are made at Your own risk. Never assume that people are who they say they are, know what they say they know, or are affiliated with whom they say they are affiliated with. Information obtained in or from Public Forums may not be reliable, and it is not a good idea to take any action based solely or largely on information You cannot confirm. We are not responsible for any Submissions by Riders, nor for any actions taken or avoided based on such Submissions. You waive any legal or equitable rights or remedies You have or may have against Us with respect to other Riders’ Submissions. \r\n16.4. Please respect and interact with other Riders as You would in any public arena. Do not reveal any information that You do not want to make public. WHEN ENGAGING IN PUBLIC FORUMS, DO NOT DISCLOSE PII. RIDERS ACKNOWLEDGE THAT PUBLIC FORUMS AND ANY CONTENT OR FEATURES OFFERED THEREIN ARE FOR PUBLIC COMMUNICATIONS AND RIDERS HAVE NO EXPECTATION OF PRIVACY WITH REGARD TO ANY SUBMISSION MADE OR RECEIVED IN A PUBLIC FORUM OR OTHERWISE IN CONNECTION WITH THE SITE.\r\n16.5. We reserve the right but not the obligation to: \r\na)	Monitor Public Forums and Submissions.\r\nb)	Condition access to and use of Public Forums in accordance with age, geographic or other criteria.\r\nc)	Review, refuse, remove, modify, store, or otherwise take any action with respect to Submissions, at Our sole discretion and without prior notice. \r\nd)	To the extent permitted by applicable law, identify any Rider and/or disclose any Submission or PII to third parties, when We believe that such identification or disclosure will facilitate compliance with laws or help to enforce these Terms and Conditions. \r\nWe encourage Riders to report to Us any suspected violations of this Agreement or any other additional terms posted on the Site.\r\n17. Privacy\r\n17.1. Your use of the Site and Services, and certain information about Riders, is subject to Our Privacy Policy, located at www.goArrive5.com/terms/privacy which is hereby incorporated and made part of this Agreement. By accessing this Site, Riders consent to the collection and use of information as described in Our Privacy Policy, as same may be amended by Us from time to time. \r\n17.2. You hereby provide Company with full, irrevocable authority to provide data concerning Your name, mobile telephone number, journey and pick-up time to Company’s pool of Transportation Providers and drivers in your location. Additionally, if you provide additional information through the Special Requests feature of the app (for example, that you are traveling with a service animal), that information will be disclosed to the Transportation Providers. This data will be used to select Your Transportation Provider from Company’s pool and will enable the Transportation Provider to pick You up at the chosen location and take You to Your destination. No other data about You will be disclosed to the pool of Transportation Providers or Your specific Transportation Provider. \r\n17.3. This Site and the App are not directed at children under the age of thirteen (13) and do not knowingly collect any PII from children under the age of 13. If a parent or guardian believes that this Site or the App have collected the PII of a child under the age of 13, please contact Us at help@Arrive5.com or by mail to our address at 14900 East Orange Lake Blvd., Kissimmee, FL 74747. \r\n17.4. You acknowledge and accept that, despite our efforts, there may be times or situations when your PII is inadvertently disclosed by Us or by a third party to whom we have disclosed Your PII. You hereby accept that risk and waive any and all claims, causes of action, damages and liability against Us in the event of inadvertent or disclosure of PII due to breach of Our internal controls. \r\n18. Links to Third Party Websites\r\n18.1. The Site may contain links and references to Websites of others. We may, from time to time, at Our sole discretion, add or remove links to other Websites. These links are provided solely as a convenience to You, and access to any such Websites is at Your own risk. We do not review, approve, monitor, endorse, guarantee, warrant, make any representations with respect to, nor are We responsible for, such Websites. In no event will We be responsible for the information contained in such Websites, their practices or for Your use of or inability to Use such Websites or their services, or transmissions received from such sites. By using the Site, You expressly relieve Us from any and all liability arising from Your use of any third-party Website. We encourage Riders to read the privacy policies and other terms and familiarize themselves with the privacy practices of the other Websites before using their services.\r\n19. Links to the Site\r\n19.1. Subject to the terms of this Agreement, Riders may display a link to the Site as long as such use is not misleading, illegal or defamatory, and the linked Website contains no infringing or illegal content. Riders may not suggest that We endorse, guarantee, sponsor, nor in any way are We responsible for or affiliated with their site, nor tarnish, blur or dilute the quality of Our trademarks or any associated goodwill. \r\n20. Disclaimers of all Warranties\r\n20.1. THE SITE, THE ARRIVE5 APP AND THEIR CONTENTS AND THE SERVICES ARE PROVIDED ON AN “AS IS” AND “AS AVAILABLE” BASIS. TO THE FULLEST EXTENT PERMITTED BY APPLICABLE LAW, WE EXPRESSLY DISCLAIM ALL WARRANTIES OF ANY KIND, WHETHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT.\r\n20.2. WITHOUT LIMITING THE FOREGOING, WE MAKE NO WARRANTY THAT THE SITE, THE ARRIVE5 APP OR ITS CONTENT OR THE SERVICES SHALL MEET YOUR REQUIREMENTS, OR SHALL BE AVAILABLE, UNINTERRUPTED, CONTINUOUS, TIMELY, SECURE, OR ERROR OR VIRUS FREE; NOR DO WE WARRANT OR MAKE ANY REPRESENTATIONS REGARDING THE USE OR THE RESULTS OF THIS SITE OR ITS CONTENT OR THE SERVICES IN TERMS OF ITS CORRECTNESS, COMPLETENESS, AVAILABILITY, ACCURACY, RELIABILITY OR OTHERWISE, OR IN CONNECTION WITH RIDERS’ SUBMISSIONS. SUBJECT TO APPLICABLE LAW, RIDERS ASSUME THE ENTIRE COSTS OF ANY AND ALL REPAIR OR CORRECTION. YOUR USE OF THIS SITE AND ITS CONTENT AND RIDERS’ SUBMISSIONS IS AT YOUR OWN DISCRETION AND RISK, AND YOU SHALL BE SOLELY RESPONSIBLE FOR ANY RESULTING CONSEQUENCES.\r\n20.3. YOU HEREBY IRREVOCABLY WAIVE ANY CLAIM AGAINST US WITH REGARD TO CONTENT AND ANY CONTENT YOU PROVIDE TO THIRD PARTY SITES (INCLUDING, BUT NOT LIMITED TO, CREDIT CARD INFORMATION AND OTHER PII), TO THE FULLEST EXTENT PERMITTED BY APPLICABLE LAW.\r\n20.4. WE DO NOT CLAIM OR PROMISE THAT ANY RIDES OR DELIVERIES ARRANGED USING THE SITE OR THE ARRIVE5 APP WILL IN FACT OCCUR; THAT DRIVERS WILL PICK UP PASSENGERS OR DELIVERIES ON TIME OR AT ALL; THAT RIDERS WILL REACH THEIR DESTINATION ON TIME, OR AT ALL. THAT RIDERS WILL RECEIVE THEIR DELIVERIES ON TIME, OR AT ALL. WE ALSO DO NOT MAKE ANY CLAIM OR PROMISE REGARDING THE TIMING, DURATION, QUALITY OR SAFETY OF THE RIDE. YOU HEREBY AGREE AND ACKNOWLEDGE THAT WE HAVE MADE NO SUCH CLAIMS OR PROMISES AND THAT WE SHALL NOT BE LIABLE FOR ANY RESULTING DAMAGES OR LOSSES.\r\n20.5. CERTAIN JURISDICTIONS DO NOT ALLOW LIMITATIONS ON IMPLIED WARRANTIES OR THE EXCLUSION OR LIMITATION OF CERTAIN DAMAGES. IF YOU RESIDE IN SUCH A JURISDICTION, SOME OR ALL OF THE ABOVE DISCLAIMERS, EXCLUSIONS, OR LIMITATIONS MAY NOT APPLY TO YOU, AND YOU MAY HAVE ADDITIONAL RIGHTS\r\n21. Indemnification\r\n21.1. RIDERS SHALL BE RESPONSIBLE FOR MAINTAINING THE CONFIDENTIALITY OF THEIR ACCOUNT INFORMATION AND PII, AS WELL AS ALL ACTIVITIES THAT OCCUR UNDER THEIR ACCOUNT. RIDERS HEREBY RELEASE, AND ACKNOWLEDGE AND AGREE, AT RIDERS’ OWN EXPENSE, TO INDEMNIFY, DEFEND AND HOLD HARMLESS US, OUR OFFICERS, DIRECTORS, EMPLOYEES, AGENTS, LICENSEES, DISTRIBUTORS, REPRESENTATIVES, AFFILIATES, AND SUCCESSORS FROM ALL LIABILITIES, CLAIMS, ALLEGED CLAIMS, LOSS AND DAMAGES (OF EVERY KIND, WHETHER KNOWN OR UNKNOWN AND SUSPECTED OR UNSUSPECTED), INCLUDING SETTLEMENT COSTS AND ANY LEGAL OR OTHER FEES AND EXPENSES FOR INVESTIGATING OR DEFENDING ANY ACTIONS OR THREATENED ACTIONS AND REASONABLE ATTORNEY’S FEES RELATED IN ANY WAY TO OR ARISING OUT OF THE USE OF OR ACCESS TO THIS SITE, ITS CONTENT, PII, RIDER SUBMISSIONS OR THE SERVICES. RIDERS SHALL USE THEIR BEST EFFORTS TO COOPERATE WITH US IN THE DEFENSE OF ANY CLAIM.\r\n22. Limitation of Liability and Damages Cap\r\n22.1. LIMITATION OF LIABILITY AND EQUITABLE RELIEF. IN NO EVENT SHALL WE, OR ANY OF OUR RESPECTIVE OFFICERS, DIRECTORS, EMPLOYEES, SHAREHOLDERS, AFFILIATES, AGENTS, SUCCESSORS OR ASSIGNS, NOR ANY PARTY INVOLVED IN THE CREATION, PRODUCTION OR TRANSMISSION OF THE SERVICES, BE LIABLE TO YOU OR ANYONE ELSE FOR ANY INDIRECT, SPECIAL, PUNITIVE, INCIDENTAL OR CONSEQUENTIAL DAMAGES (INCLUDING, WITHOUT LIMITATION, THOSE RESULTING FROM BODILY INJURY, INJURY TO PROPERTY, DEATH, LOST PROFITS, LOST DATA OR BUSINESS INTERRUPTION) ARISING OUT OF: (i) THE USE, INABILITY TO USE, OR THE RESULTS OF USE OF THE SERVICES, ANY WEBSITES LINKED TO THE SITE OR TO THE ARRIVE5 APP, OR THE MATERIALS, INFORMATION OR SERVICES CONTAINED ON ANY OR ALL SUCH WEBSITES OR ON THE ARRIVE5 APP, (ii) ANY CONDUCT BY OR ON BEHALF OF US THAT CONSTITUTES, OR MAY CONSTITUTE, A CIVIL VIOLATION OF THE COMPUTER FRAUD AND ABUSE ACT OF 1984 AND AS MAY BE AMENDED FROM TIME TO TIME, OR (iii) ANY VIRUSES THAT MAY INFECT, YOUR COMPUTER, MOBILE DEVICE, TELECOMMUNICATION EQUIPMENT, OR OTHER PROPERTY CAUSED BY OR ARISING FROM YOUR ACCESS TO, USE OF, OR BROWSING OF THE SITE OR THE ARRIVE5 APP, OR YOUR DOWNLOADING OF ANY INFORMATION OR MATERIALS FROM US, WHETHER BASED ON WARRANTY, CONTRACT, TORT OR ANY OTHER LEGAL THEORY AND WHETHER OR NOT ADVISED OF THE POSSIBILITY OF SUCH DAMAGES. ALL RIDERS, SUBSCRIBING ORGANIZATIONS (INCLUDING WITHOUT LIMITATION ANY OFFICERS, DIRECTORS, SHAREHOLDERS AND EMPLOYEES THEREOF), AND ANY AGENTS, REPRESENTATIVES OR ASSIGNS THEREOF, HEREBY WAIVE ANY CLAIMS FOR EQUITABLE RELIEF AGAINST US, OUR RESPECTIVE OFFICERS, DIRECTORS, EMPLOYEES, SHAREHOLDERS, AFFILIATES, AGENTS, SUCCESSORS OR ASSIGNS, ARISING OUT THE SERVICES OR FOR THE REASONS SET FORTH HEREIN, WHETHER OR NOT SUCH SERVICES WERE USED BY SUCH PERSONS OR ENTITIES.\r\n22.2. DAMAGES CAP. IN NO EVENT SHALL WE OR OUR AFFILIATES, CONTRACTORS, EMPLOYEES, AGENTS, OR THIRD PARTY PARTNERS, LICENSORS OR SUPPLIERS ASSUME TOTAL LIABILITY TO YOU FOR ANY DAMAGES, LOSSES, AND/OR CAUSES OF ACTION ARISING OUT OF OR RELATING TO THESE TERMS OR YOUR USE OF THE SERVICES (WHETHER IN CONTRACT, TORT (INCLUDING NEGLIGENCE), WARRANTY, OR OTHERWISE) EXCEED ONE HUNDRED DOLLARS ($100.00).\r\n22.3. REFERENCE SITES AND THIRD-PARTY APPLICATIONS. THE LIMITATIONS ON LIABILITY AND DAMAGES CAP SET FORTH ABOVE IN THIS PARAGRAPH SHALL ALSO APPLY WITH RESPECT TO DAMAGES INCURRED BY REASON OF ANY PRODUCTS OR SERVICES SOLD OR PROVIDED ON ANY REFERENCE SITES AND THIRD PARTY APPLICATIONS OR OTHERWISE BY THIRD PARTIES OTHER THAN US AND RECEIVED BY YOU THROUGH OR ADVERTISED ON THE SITE OR THE ARRIVE5 APP, OR THE SERVICES OR RECEIVED BY YOU THROUGH ANY REFERENCE SITES AND THIRD PARTY APPLICATIONS.\r\n22.4. EFFECT OF STATE LAW. THE LIMITATIONS ON LIABILITY AND DAMAGES CAP SET FORTH IN THIS SECTION ARE ENFORCEABLE TO THE FULLEST EXTENT PERMITTED UNDER APPLICABLE LAW. CERTAIN STATES’ LAWS MAY NOT RECOGNIZE OR OTHERWISE MAY LIMIT SOME OF THE LIMITATIONS OF LIABILITY AND DAMAGES CAP SET FORTH HEREIN. IF SO, YOU MAY HAVE ADDITIONAL RIGHTS.\r\n23. Basis of the Bargain\r\n23.1. YOU ACKNOWLEDGE AND AGREE THAT WE HAVE OFFERED OUR SERVICES, SET OUR PRICES AND ENTERED INTO THESE TERMS IN RELIANCE UPON THE DISCLAIMERS OF WARRANTIES, THE LIMITATIONS OF LIABILITY AND LIABILITY CAP SET FORTH HEREIN; THAT THE DISCLAIMERS OF WARRANTIES, THE LIMITATIONS OF LIABILITY AND LIABILITY CAP SET FORTH HEREIN REFLECT A REASONABLE AND FAIR ALLOCATION OF RISK BETWEEN YOU AND US; AND THAT THE DISCLAIMERS OF WARRANTIES, THE LIMITATIONS OF LIABILITY AND LIABILITY CAP SET FORTH HEREIN FORM AN ESSENTIAL BASIS OF THE BARGAIN BETWEEN YOU AND US. YOU ACKNOWLEDGE AND AGREE THAT WE WOULD NOT BE ABLE TO PROVIDE THE SERVICES TO YOU ON AN ECONOMICALLY REASONABLE BASIS WITHOUT THESE LIMITATIONS.\r\n24. Releases\r\n24.1. The Site and the Arrive5 App provide You with access to a large number of independent Transportation Providers in your locality. Your contract for the hire of Services is directly with your Transportation Provider. You agree and acknowledge that We are not liable to you, and you hereby waive any claims, causes of action, damages or liabilities, against Us with respect to any claim for injury to person or property, loss of property, or death, arising from (a) the conduct or negligence of the Transportation Providers and their drivers, (b) the manufacture or use of vehicles provided by the Transportation Provider, or (c) breach of any contract you may have directly with the Transportation Provider or driver. \r\n24.2. YOU HEREBY RELEASE COMPANY, OUR OFFICERS, DIRECTORS, EMPLOYEES, AGENTS, LICENSEES, DISTRIBUTORS, REPRESENTATIVES, AFFILIATES, AND SUCCESSORS FROM CLAIMS, DEMANDS ANY AND ALL LOSSES, DAMAGES, RIGHTS, CLAIMS, AND ACTIONS OF ANY KIND INCLUDING, WITHOUT LIMITATION, PERSONAL INJURIES, DEATH, AND PROPERTY DAMAGE, THAT IS EITHER DIRECTLY OR INDIRECTLY RELATED TO OR ARISES FROM YOUR USE OF THE SITE, ARRIVE5 APP, OR SERVICES.\r\n25. Governing Law; Arbitration of Disputes; Waiver of Class Action Claims\r\n25.1. This Agreement will be governed by and construed in accordance with the laws of the State of New York applicable to agreements made and fully performed therein. Any and all controversies, disputes or claims arising out of or relating to this Agreement and its terms or a breach thereof shall be submitted to arbitration before a single arbitrator and in accordance with the Commercial Rules then obtained of the American Arbitration Association (“AAA”). The decision of the arbitrator shall be final and binding upon the parties, and judgment upon the award may be entered or enforced in any Court having jurisdiction thereof. The location of any arbitration proceedings hereunder shall be in the City and State of New York, and shall be at such location within said jurisdiction as shall be agreed upon by the parties, or failing such agreement, the facilities of the AAA. All proceedings shall be kept strictly confidential by all involved. \r\n25.2. YOU AND COMPANY AGREE THAT EACH OF US MAY BRING CLAIMS AGAINST THE OTHER ONLY ON AN INDIVIDUAL BASIS AND NOT AS A PLAINTIFF OR CLASS MEMBER IN ANY PURPORTED CLASS OR REPRESENTATIVE ACTION OR PROCEEDING, UNLESS BOTH YOU AND COMPANY AGREE OTHERWISE. THE ARBITRATOR MAY NOT CONSOLIDATE OR JOIN MORE THAN ONE PERSON’S OR PARTY’S CLAIMS AND MAY NOT OTHERWISE PRESIDE OVER ANY FORM OF A CONSOLIDATED, REPRESENTATIVE OR CLASS PROCEEDING. ADDITIONALLY, THE ARBITRATOR MAY AWARD RELIEF (INCLUDING MONETARY, INJUNCTIVE AND DECLARATORY RELIEF) ONLY IN FAVOR OF THE INDIVIDUAL PARTY SEEKING RELIEF AND ONLY TO THE EXTENT NECESSARY TO PROVIDE RELIEF NECESSITATED BY THAT PARTY’S INDIVIDUAL CLAIM(S). THE ARBITRATOR SHALL HAVE NO AUTHORITY TO AWARD PUNITIVE, CONSEQUENTIAL, SPECIAL OR INDIRECT DAMAGES. ANY RELIEF AWARDED CANNOT AFFECT OTHER RIDERS.\r\n26. No Representations\r\n26.1. We make no representation that the Site, its Content or the Services are appropriate or available for use in any particular location. Those who choose to access the Site or the Services do so on their own initiative and are responsible for compliance with all applicable laws including any applicable local laws. \r\n27. Limitation of Claims\r\n27.1. You agree that regardless of any statute or law to the contrary, any claim or cause of action arising out of or related to use of the Agreement must be filed within one (1) year after such claim or cause of action arose or be forever barred. \r\n28. Infringement Notices and Takedown\r\n28.1. We comply with the Digital Millennium Copyright Act (“DMCA”). We respect the intellectual property of others. If you believe in good faith that any content on the Site or the Arrive5 App infringes the copyright owned by you or a third party, please contact our legal@Arrive5.com\r\nThe notice must contain the following information: \r\no	A physical or electronic signature of the owner, or a person authorized to act on behalf of the owner, of the copyright that is allegedly infringed; \r\no	Identification of the copyrighted work allegedly infringed;\r\no	Identifying information reasonably sufficient to allow determination by Us of the location of the material that is allegedly infringing; \r\no	Information reasonably sufficient to permit Us to contact you;\r\no	A statement that you have a good faith belief that use of the material in the manner complained of is not authorized by the copyright owner, its agent, or the law; and \r\no	A statement that the information in the notification is accurate, and under penalty of perjury, that you are authorized to act on behalf of the owner of an exclusive right that is allegedly infringed. \r\n28.2. The Copyright Agent will only respond to claims involving alleged copyright infringement. We may give notice that We have removed or disabled access to certain material by means of a notice posted on Our Site, an email to a User, or by written communication via first claim mail to a User. If a User receives such notice, the User may submit counter-notification in writing to the designated agent. To be effective, the counter-notification must be a written communication and contain the following information: (a) the User’s physical or electronic signature; (b) a description of the material that was removed or to which access was disabled and the location at which the material appeared on the Site before it was removed or disabled; (c) a statement under penalty of perjury that the User has a good faith belief that the material was removed or disabled due to mistake or misidentification; and (d) the User’s name, physical address and telephone number, and a state that the User consents to the jurisdiction of a court for the judicial district in which the User is located, and that the User will accept service of process from the complainant. Notwithstanding this section, We reserve the right in Our sole discretion, at any time, to remove content which appears to infringe the intellectual property rights of another person or entity. \r\n28.3. You acknowledge that if you fail to comply with all of the requirements of this section, your DMCA notice may not be valid. For any questions regarding this procedure, or to submit a complaint, please contact the representative designed above. \r\n28.4. We reserve the right to terminate any User’s access to the Site or the Get App if We determine, in Our own discretion, that the User is a repeat infringer. \r\n29. International Users and International Travel\r\n29.1. This Site is controlled, operated and administered by Company from Our offices within the USA. If You access the Site from a location outside the USA, You are responsible for compliance with all local laws. You agree that You will not use the Site or any Content accessed through the Site in any country or in any manner prohibited by any applicable laws, restrictions or regulations. \r\n29.2. Different Terms and Conditions may apply if the Arrive5 App is used outside the USA. Use of the Arrive5 App outside the USA constitutes your acknowledgement that you have read, understand and unconditionally agree to the other and/or different terms applicable in the Country in which the Arrive5 App is used. \r\n29.3. Some banks and credit card companies impose fees for international transactions including without limitation foreign transaction and conversion fees. These fees are determined solely by Your bank or credit card company and We are not responsible for any such fees. If You have any questions about these fees or the exchange rate applied to Your Reservation, please contact Your bank or credit card company. \r\n30. General Provisions\r\n30.1. No waiver of any term of this Agreement shall be deemed a further or continuing waiver of such term or any other term, and any failure to assert any right or provision under this Agreement shall not constitute a waiver of such term. \r\n30.2. If for any reason a court of competent jurisdiction finds any provision or portion of this Agreement to be unenforceable, then such provision or portion shall be construed, as nearly as possible, to reflect the original provision, and the remainder of the Agreement will continue in full force and effect. \r\n30.3. This Agreement, and any rights and licenses granted hereunder, may not be transferred or assigned by You, but may be assigned by Us without restriction. \r\n30.4. This Agreement, the Additional Terms, any other terms on Our Site and Our Privacy Policy comprise the entire agreement between You and Us, state Our and the Transportation Providers’ entire liability, and Your exclusive remedy with respect to the Site and Services, and supersede all prior agreements pertaining to the subject matter of this Agreement and such terms, rules and policies. \r\n30.5. The section titles in this Agreement are used solely for the convenience and have no legal or contractual significance. No provision of this Agreement shall be construed against the owners of this site but rather shall be construed in a neutral and fair manner as terms entered into by a fully-informed party on a voluntary basis. \r\n30.6. The terms of this Agreement, which by their nature should survive the termination of the Agreement, shall survive such termination. \r\n30.7. The heading references herein are for convenience purposes only, do not constitute a part of these Terms, and shall not be deemed to limit or affect any of the provisions hereof. \r\n30.8. You agree that no joint venture, partnership, employment, or agency relationship exists between You and Us as a result of these Terms or use of the Site, the Arrive5 App or the Services. You further acknowledge that by submitting User Submissions or other Content, no confidential, fiduciary, contractually implied or other relationship is created between you and Us other than pursuant to these Terms. \r\n30.9. We may give notice to You by email, a posting on the Site, or other reasonable means. You must give notice to Us in writing via mail, via email to help@Arrive5.com, or as otherwise expressly provided. If You have any questions or concerns about our App or Site, our Services or your Reservation, or any other questions, You may contact Us via email at help@Arrive5.com\r\n', '1', '2019-10-01 10:35:54');
INSERT INTO `faq` (`id`, `title`, `description`, `status`, `added_on`) VALUES
(7, 'ARRIVE5 TECHNOLOGY, INC.PRIVACY POLICY', '1.	ARRIVE5TECHNOLOGY, INC.PRIVACY POLICY\r\n1.1.ARRIVE5 Technology, Inc. (“ARRIVE5”, “we”, “our”, and/or “us”) is a company committed to protecting and respecting your privacy, as user or person contacting, visiting, or otherwise submitting information to ARRIVE5. \r\n1.2. Safeguarding your Personal Information is important to ARRIVE5 and it recognize the responsibility you entrust it when providing your Personal Information. This Privacy and Cookie Policy (“Privacy Policy”) details how your Personal Information is used by ARRIVE5 or its affiliates anywhere in the world[1], both actively and passively, when you use a ARRIVE5 website, (“Site”), and/or download or use the ARRIVE5 Mobile Application (”App”), whether you are a driver, fleet, transportation provider, or courier (in each case, a “Driver”), rider, delivery recipient or sender (in each case, a “Rider”), App user, Site visitor, office visitor, or job applicant. With this policy (together with ARRIVE5\'s Terms & Conditions and any other documents referred to therein) ARRIVE5 wishes to outline the basis on which any Personal Information ARRIVE5 collects from you, your representative, or employer, or that you provide to ARRIVE5, will be processed including, what information it may collect from you via the Site and/or the App, how it will use it, how it may disclose information provided by you to third-parties and the use of “cookies” or similar files or tags on the Site and by the App. \r\n1.3.ARRIVE5 is committed to safeguarding your Personal Information (as defined below) in line with applicable data protection laws. Please read the following carefully to understand ARRIVE5\'s views and practices regarding your personal data and how ARRIVE5 will treat it. \r\n2.	USER\'S ACKNOWLEDGEMENT OF THIS POLICY\r\n2.1.By using ARRIVE5\'s services (the “Services”) or registering, downloading information or entering the Site and/or the App you acknowledge that you are or have had the opportunity to become aware of this Privacy Policy and ARRIVE5’s practices described therein, including the processing (including collecting, using, disclosing, retaining or disposing) of your information under the terms of this policy. The information ARRIVE5 holds about you may be held and processed on computer and/or paper files. \r\n2.2.In the event where you provide ARRIVE5 with any information regarding another person, you procure that you have made them aware of this privacy policy. \r\n2.3.If you have any questions or comments regarding privacy issues on the Site and/or the App, please contact ARRIVE5 by email at privacy@ARRIVE5.com. \r\n3.	TYPES OF INFORMATION WE COLLECT\r\n3.1.ARRIVE5 collects both “Personal Information” and “Anonymous Information” about our users and visitors. Personal Information is information that can be used to contact or identify you, such as your full name, email address, phone number, payment method details, login name, password, mailing address, IP address, and profile picture, as well as information that is linked to such information. “Anonymous Information” is information that we cannot use to contact or identify you and is not linked to information that can be used to do so. It includes passively collected information about your activities on the Site or on the App, such as usage data, to the extent that information is not linked to your Personal Information. \r\n3.2. You can access and browse certain portions of the Site and download the App without disclosing Personal Information, although, like most website and mobile app providers, we passively collect certain information from your devices, such as your IP address, screen resolution information, geographic location, Wifi information, browser information, unique device identifier (“UDID”) where applicable and/or your mobile operating system, and use third-parties such as Mixpanel and other SDK’s to obtain detailed analytics on the device. We also collect geolocation data from visitors that download and open the App. Sometimes, this passively collected information will constitute Personal Information - please see Sections 3.1.5 and 3.1.7 in particular. Please note that you can choose not to provide us with certain information (for instance, by adjusting your cookie settings, see Section 5), and it is your voluntary decision whether to provide us with any such information but choosing not to will limit the features of the Site or the App you can access and use. \r\n3.3. Voluntarily submitted information. We collect information that you provide to us during your use of the Site, and/or App. Some examples include: \r\n3.3.1. Personal Information that you enter when registering for or using the App, including your full name, email address, phone number, payment method, billing information, profile picture and profile information; \r\n3.3.2. communications with us (e.g. emails, text messages) or with other Riders or Drivers (e.g. verbal or written instructions to Drivers); \r\n3.3.3. information you provide in your user preferences or surveys, to customer support services or customer support calls or that you post on any forums or message boards, including social media and blogs. We may record your customer support calls for quality assurance purposes and for improving our services; \r\n3.3.4. the provision of services or online content to deal with your requests and enquiries. This includes data you provide at the time of: registering to use the Site and/or the App; subscribing to any services or ARRIVE5 offers; downloading information posted on the Site and/or the App; posting material; or requesting further services from ARRIVE5; \r\n3.3.5. data you provide to us or to a third-party in connection with third-party loyalty schemes with which ARRIVE5 partners, including loyalty ID or scheme membership number and information about points accrued; \r\n3.3.6. data you provide, including photos or comments, in connection with or participation in any promotions or competitions sponsored, promoted or offered by ARRIVE5 and/or any third-party and any data provided to ARRIVE5 by way of feedback, profile forms or Site/App issues; \r\n3.3.7. data you provide, including a copy of your identification or credit card, in connection with instances where ARRIVE5 needs to authenticate user identity and/or ownership of the payment method provided; \r\n3.3.8. information about you which you provide when corresponding with ARRIVE5 by email, post, the App, sms, messenger, telephone or otherwise, in which case ARRIVE5 may keep a record of that correspondence to respond to your enquiries and improve its services; \r\n3.3.9. where you are a Driver, insurance policies and licensing information; and \r\n3.3.10. other information from your interaction with the Site and/or the App, services, content and advertising. With regard to each of your visits to the Site and/or use of the App, ARRIVE5 may automatically collect authentication data, technical information and information about your visit, including computer and connection information, statistics on page views, traffic to and from the Site, ad data, IP address, standard web log information and the resources that you access. \r\n3.4. Information collected through use of the Site or the App \r\n3.4.1 Geolocation data: If you access the App through a mobile device, we may access, collect, monitor and/or remotely store “geolocation data”, which may include the GPS coordinates of your trip (including pickup and drop-off data and route information) or similar information regarding the location of your mobile device. \r\n3.4.2. Contacts: We may access your address book, calendar, or contacts if you provide us with your permission to do so. \r\n3.4.3. Transaction data: We collect information created during your various interactions with the Service, including the date and time of any ride using the ARRIVE5 Service (a “Ride”), distance of Ride, amount charged, including breakdown of base fee, tip and additional fees, as well as promotional code delivery. \r\n3.5. Information collected via technology \r\n3.5.1. If you are using our Website, we collect information from you, including your hardware model, browser type, operating system, Internet Protocol (IP) and domain name. If you are using a mobile device, we may also receive your UDID, or another unique identifier, and mobile operating system. We may correlate this information with other Personal Information we have about you. We may also use cookies and URL information to gather information regarding the date and time you used the Service and the information for which you searched and accessed. \r\n3.5.2. In connection with your use of the App, we may receive your call data, including the date and time of calls and SMS messages with ARRIVE5 or Drivers, the parties\' phone numbers and the content of those SMS messages. \r\n3.5.3. We may also use third-party tracking services, such as Google Analytics, and usage logs, to track and analyse data from users of the Service for purposes of security, fraud prevention, and money laundering prevention. \r\n3.5.4. If you choose to remit payment for a Ride via a third-party payment provider (e.g. Google Wallet), your Personal Information (excluding full payment card information) obtained by the payment provider may be given to us by such payment provider. \r\n3.5.5. If you choose to login to the App or Site via a third-party social media website (e.g. Facebook), your Personal Information may be provided to us by such social media website. \r\n3.5.6.ARRIVE5 may also share your data with its third-party statistical analytics service provider, solely for the provision of analytics and to better understand its users. \r\n3.5.7. Because we rely on third-party commercial software and programs to operate our servers, we may inadvertently collect Personal Information due to certain automatic functions or features contained in such software or due to certain software changes or upgrades. We will use reasonable efforts to remove any such information after we discover such information in our system. If you know of any such unintended collections, please notify us promptly so we can take the necessary action to remove such information from our system. \r\n3.6. Information provided by others \r\n3.6.1.We collect information that Riders provide about Drivers, and vice-versa, including via ratings and postings on forums or message boards. \r\n\r\n3.6.2. ARRIVE5 may receive information about you if you use any of the other websites it operates or the other services it provides. ARRIVE5 also works closely with third parties (including, for example, business partners, subcontractors in technical, payment and delivery services, advertising networks, analytics providers, search information providers, credit reference agencies) and may receive information about you from them. Where third-parties collect information about you and share it with us, you should refer to their separately maintained privacy policies or notices. \r\n3.7. Information about your device \r\n3.7.1.ARRIVE5 collects information about the device you use to access the Site, App and/or Services, which may include personal data. This information is used to recognize your device so that it can be linked to your account. This activity is carried out for the purposes of safety, security, evaluation of the performance of the app or the Site, improvement of the customer experience, fraud prevention and involves the sharing of information about your device including limited personal data, IP address, WiFi and location information with a third-party service provider. \r\n3.7.2. the type, name and use of your device, browser (e.g. the Internet browser you are using such as: Chrome, Internet Explorer, Firefox, and your browser settings) and installed applications and widgets (“Installed Apps”), including the Installed Apps\' name, ID, system, installed date, update date, version, and whether it is on the home page. We also collect your interaction with those Installed Apps, including opening, closing, or uninstalling, and the duration of your use. We do not collect data within the Installed Apps, but rather solely aggregated data concerning use itself; \r\n3.7.3. the battery and network performance (i.e. battery status and charger use) of your device and the file names, types and files sizes on your device, including the amount of free and used local storage space, but not the contents of the files; and \r\n3.7.4. the system status, including device event information such as crashes and system activity. \r\n4. COOKIES AND OTHER WEB FILES OR TAGS\r\n4.1. In the interest of giving our Site an attractive appearance and in order to allow the use of certain functions, we use cookies on various pages of our Site, as well as other files and tags (e.g. JavaScript tags). Cookies consist of small text files that are stored on your device. \r\n4.2. Most of these cookies used by us will be erased from your hard drive immediately after your browser session (so-called session cookies). \r\n4.3. Other cookies remain saved to your device\'s hard drive and enable us to recognize your device in the event of a later visit to our website (so-called persistent cookies). It is particularly these cookies that allow us to make our Site more user-friendly, effective and safe. Thanks to these files, for example, it is possible to display information specifically suited to your personal preferences on a certain webpage. A cookie browser session lasts for the duration of 18 hours. \r\n4.4. The cookies will, among other things, track clicks, and online activity. \r\n4.5. For the same reason, the Site and the App both use cookies and other local files to distinguish you from other users. ARRIVE5 may use a cookie or configuration file, which is stored on the browser or hard drive of your computer and/or your mobile telephone device, to obtain information about your general internet usage. Cookies and other such files contain information that is transferred to your computer\'s and/or mobile telephone device\'s hard drive. They help ARRIVE5 to improve the Site and the App, and to deliver a better and more personalized service. They enable ARRIVE5: \r\n4.5.1. to estimate ARRIVE5\'s audience size and usage pattern and perform other analytics; \r\n4.5.2. to gather information about your approximate geographic location (e.g. city) to provide localized content; \r\n4.5.3. to store information about your preferences and to allow ARRIVE5 to customized the Site and/or App according to your individual interests and the device or browser you are using; \r\n4.5.4. to continually improve ARRIVE5\'s Services; \r\n4.5.5. to recognize you when you return to the Site and/or App; and \r\n4.5.6. to prevent fraud and/or abuse. \r\n4.6.The legal grounds for processing the information contained in cookies and other such files or tags consists of the performance or entering into of a contract with you (where cookies are necessary as part of the customer journey), or otherwise our legitimate interests in a safe and user-friendly presentation of our Site. \r\n4.7. We remind you however that, in the event of limited cookie settings on your computer, you may no longer be able to use functions of our Site to the fullest extent possible. \r\n4.8. Google Analytics\r\n4.8.1.This Site uses Google Analytics, a web analysis service provided by Google Inc. (“Google”) as well as Mixpanel and other SDK’s of this nature. Google Analytics makes use of cookies. In principle, the information about your use of our website as displayed by the cookie is transferred to and stored on the Google server in the US. We activate a so-called IP anonymization on this website, which means that Google shortens the IP addresses from within EU Member States or from other EEA treaty states before they are exported to the US server. Only in exceptional cases will the full IP address be transferred to the US Google server before it is shortened in the US. \r\n4.8.2. As instructed by us, Google will use the information for the purpose of analyzing your use of our Site, to compose reports on the activities of our Site and to provide further services to us related to the use of the website and the internet. The IP address that is transferred by your browser and that is shortened before storing for the purposes of Google Analytics will not be added to other Google data. You can prevent the storage of the cookies through browser settings (see section 5.8), however we remind you that, in certain instances, this may cause a reduction in the functionality of the Site. More information about Google Analytics\' use of your data can be on the Google Privacy Policy: https://www.google.com/policies/privacy/. Google provides a browser-add on which allows users to opt-out of Google Analytics across all websites which can be downloaded here: https://tools.google.com/dlpage/gaoptout. \r\n4.9. Retargeting\r\n4.9.1.This website makes use of retargeting technologies that are administered by other operators. Through retargeting, users of our partners\' websites, who were interested in our Site and in our Services before, can be approached with personalized advertisements. Studies show that the inclusion of personalized advertisements that are related to people\'s actual interests are more appealing to the user than advertisements without any personal connection. Through retargeting, the incorporation of advertisements is based on the analysis of cookies that display earlier user\'s conduct. If you wish to object to this mode of advertisement, you can deactivate cookies and/or delete existent cookies through your browser settings. You can do so by following the instructions under “Cookies”.\r\n4.9.2. The legal basis for the processing of your data along with the use of collective analytical tools and internet technologies is our legitimate interest in obtaining an analysis of the activities on our website and the user\'s and surfer\'s behavior on the Site, notably for research, BI, and product development, as well as our legitimate interest (and the interest of third-parties) in the inclusion of personalized advertisements suited to your interests. \r\n5. CLICKSTREAM DATA \r\nAs you browse the Internet, a trail of electronic information is left at each web site you visit. This information, which is sometimes called “clickstream” data, can be collected and stored by a web site\'s server. Clickstream data can tell us the type of computer and browsing software you use, the address of the web site from which you linked to the Site, and in some instances, your email address. We may use clickstream data to determine how much time visitors spend on each page of the Site and how they navigate through the Site. We will only use this information to improve or customize the Site. \r\n6. USES MADE OF THE INFORMATION\r\n6.1.ARRIVE5 uses information held about you (including both Personal and Anonymous Information) in the following ways: \r\n6.1.1. to register and administer your account; \r\n6.1.2. to provide our various Services, as further detailed on our Site; \r\n6.1.3. to facilitate communications between Riders and Drivers; \r\n6.1.4. to offer customer support; \r\n6.1.5. to ensure that content on the Site and/or the App is presented in an effective manner for you and for your device (e.g. PC, mobile telephone, tablet); \r\n6.1.6. to provide you with information about products or Services that you receive from ARRIVE5 (sometimes called “Service Messages”), for example, notices of updates to our Privacy Policy and invoices from Rides; \r\n6.1.7. to carry out ARRIVE5\'s obligations arising from any contracts entered between you and ARRIVE5 (including but not limited to providing you with the information, goods and/or Services you request from it); \r\n6.1.8. to validate and reimburse valid third-party loyalty coupons for ARRIVE5 Rides and to notify third-party loyalty partners about qualifying Rides which may accrue third- party loyalty scheme points; \r\n6.1.9. to provide you with information (by email, sms, messenger, post or telephone) about other goods and Services ARRIVE5 offers that are similar to those that you have already purchased or enquired about or which may interest you, having first obtained your consent where required to do so (and subject always to your right to opt-out of marketing messages, see section 13.1); \r\n6.1.10. to provide you with information (by email, sms, messenger, post or telephone) about goods or services offered by ARRIVE5\'s promotional partners which are relevant to Services you have requested from ARRIVE5, or which we otherwise believe may interest you, having first obtained your consent where required to do so (and subject always to your right to opt-out of marketing messages, see section 13.1); \r\n6.1.11 to allow you to participate in interactive features of ARRIVE5\'s Services, when you choose to do so; \r\n6.1.12 to contact you for your views on ARRIVE5\'s Services and to notify you about changes or developments to ARRIVE5\'s Service; \r\n6.1.13 to administer the Site and the App, and for internal operations, including troubleshooting, data analysis, testing, research, statistical and survey purposes; \r\n6.1.14 as part of ARRIVE5\'s efforts to keep the Site and the App safe and secure and for the prevention of fraud; \r\n6.1.15 to measure or understand the effectiveness of any advertising ARRIVE5 serves to you and others, and to deliver relevant advertising to you; \r\n6.1.16 to improve the Site and/or the App including tailoring it to your needs; \r\n6.1.17 to use GPS to identify the location of users; \r\n6.1.18 to comply with ARRIVE5\'s legal obligations; \r\n6.1.19 to protect ARRIVE5\'s rights and interests. \r\n6.2.ARRIVE5\'s third-party service providers may cross-reference your data with data it already holds (independently of ARRIVE5) to provide ARRIVE5 with statistical analysis of the demographic of its users. ARRIVE5 uses aggregated and anonymous analytics information for internal business planning and other similar purposes. \r\n6.3. Under data protection law, all processing of personal data is justified by a “condition” for processing. In the majority of cases any processing will be justified on the basis that: \r\na.	you have consented to the processing (e.g. where you provide us with marketing consents or opt-in to optional additional services or functionality); \r\nb.	the processing is necessary to perform a contract with you (i.e. for Ride Services) or take steps to enter into a contract; \r\nc.	the processing is necessary for us to comply with a relevant legal obligation (e.g. the disclosure of information to law enforcement or tax authorities); or \r\nd.	the processing is in our legitimate commercial interests, subject to your interests and fundamental rights (e.g. monitoring which we carry out of your use of the App and the targeting of advertising). \r\n7. SECURITY AND DATA RETENTION\r\n7.1.ARRIVE5 has implemented administrative, physical and electronic measures designed to protect your information from unauthorized access. ARRIVE5 will comply with applicable law in the event of any breach of the security, confidentiality, or integrity of your Personal Information and, where we consider appropriate or where required by applicable law, notify you via email, text or conspicuous posting on the Site in the most expedient time possible and without unreasonable delay, insofar as it is consistent with (i) the legitimate needs of law enforcement or (ii) any measures necessary to determine the scope of the breach and restore the reasonable integrity of the data system. \r\n7.2. Although guaranteed security does not exist either on or off the Internet, we make commercially reasonable efforts to make the collection and security of such information consistent with this Privacy Policy and all applicable laws and regulations. \r\n7.3. Where ARRIVE5 has given you (or where you have chosen) a username, login or password which enables you to access certain parts of the Site and/or the App, you are responsible for keeping this information confidential. ARRIVE5 asks you not to share a username, login or password with anyone. \r\n7.4.ARRIVE5 restricts access to your Personal Information to individuals who need access to it in order to process it on ARRIVE5\'s behalf. These individuals, where employees, are bound by confidentiality agreements and ARRIVE5 will take appropriate action (which may include disciplinary proceedings) in the event ARRIVE5 finds that its employee(s) has failed to meet standards in looking after your personal data. ARRIVE5 cannot accept any liability for employees or agents acting outside its normal course of business, or for Drivers or any third-parties with whom your Personal Information may be shared (as described in section 1.1) who act outside of the terms of our contracts with them. \r\n7.5. We will retain your Personal Information only for as long as necessary to fulfil the purpose(s) for which it was collected and to comply with applicable laws. \r\n7.6. In general, this means that we store your data throughout the existence of your account with us or for as long as is otherwise required to deliver our Services, except where we have a lawful basis for saving it for an extended period of time (for instance, after fully executing a contract, we may still have a legitimate interest in using your contact details for marketing purposes). We also retain the data we need for the execution of pending tasks and the data we need to investigate and realize our legal rights and our claims, as well as certain data that we must store for a legally mandatory period of time. When certain data is only saved due to a legally mandatory preservation term, the processing of it by us is limited, even where you do not specifically request this. \r\n8. DISCLOSURE AND SHARING OF YOUR INFORMATION\r\n8.1. We know how important it is to keep your information confidential. We will not rent, sell or share your Personal Information with third-parties except as specifically approved by you at the time of disclosure or under the circumstances described in this Privacy Policy. \r\n8.2. If you do not want us to use or disclose Personal Information collected about you in the ways identified in this Privacy Policy, you may choose not to (i) provide your Personal Information at any time or (ii) download the App and become a Rider. \r\n8.3. In addition to using the information collected by us for the purposes described in section 6 above, we may also share your information as described below. Please review our sharing policy closely, especially with respect to your Personal Information. \r\n8.4. Riders and Drivers\r\n8.4.1. We share certain information about Riders (including but not limited to name, rating, pickup and drop-off locations, route information, profile picture) with Drivers to enable the scheduling and provision of Rides. \r\n8.4.2. Information provided on any online forum or message board may be viewed by any Riders or Drivers who access the forum or board. \r\n8.5. Service Providers We also share Personal Information with vendors, payment providers, transportation providers, promotion companies, sponsorship companies, commercial software providers, consultants, market researchers and data processers who perform services on behalf of ARRIVE5, including without limitation, companies that provide route guidance, estimated times of arrival, email services and host the Website, App and Service. We also share Personal Information with third-parties that provide analysis, monitoring, and reports to assist us in preventing and detecting fraudulent transactions and other activity. ARRIVE5 has selected companies who maintain high standards with respect to privacy and agree to use the Personal Information only to perform specific services on behalf of ARRIVE5 and in accordance with the terms and conditions of this Privacy Policy. \r\n8.6. Compliance with Laws, Law Enforcement and Safety We may disclose information we have collected about you if required to do so by law or if we, in our sole discretion, believe that disclosure is reasonable to comply with the law, requests or orders from law enforcement, or any legal process (whether or not such disclosure is required by applicable law), or to protect or defend ARRIVE5\'s, or a third-party\'s, rights or property. We may also reserve the right to disclose information we\'ve collected about you for purposes of protecting the health and safety of our Riders and Drivers, such as in the case of risk of harm or violence against any person. Finally, we may also disclose information in order to enforce or apply our Terms and Conditions https://ARRIVE5.com/terms/ and other agreements; or to protect the rights, property, or safety of ARRIVE5, its customers, or others. This includes exchanging information with other companies and organizations for the purposes of fraud protection and credit risk reduction. \r\n8.7. Anonymous Information Aggregated Anonymous Information is the combination of your Anonymous Information with the Anonymous Information of other users (“Aggregated Anonymous Information”). Aggregated Anonymous Information does not allow you to be identified or contacted. We may share such Aggregated Anonymous Information with third-parties, and, depending on the circumstances, we may or may not charge third-parties for such information, or limit the third parties\' use of the same. \r\n8.8. Business Transactions We may share all or some of your Personal Information with any of our parents, subsidiaries, joint venturers, or other companies under common control, in which case we will require them to honour this Privacy Policy. Additionally, in the event we undergo a business transition such as a merger, acquisition by another company, or sale of all or a portion of our assets, including in the unlikely event of bankruptcy, your Personal Information may be among the assets transferred. You acknowledge that such transfers may occur and are permitted by this Privacy Policy, and that any entity that acquires us, is merged with us or that acquires our assets may continue to process your Personal Information as set forth in this Privacy Policy. \r\n8.9. Third Party Marketing As referred to in section 6.1.9, ARRIVE5 may disclose your information to carefully selected partners which ARRIVE5 thinks may be of interest to you. These companies may contact you by post, email, telephone or fax for marketing or promotional purposes. \r\n9. LOCATION OF YOUR INFORMATION\r\n9.1. Your Personal Information may be disclosed to ARRIVE5 entities and third-parties in jurisdictions in the United States of America.\r\n9.2. Consequently, the data that ARRIVE5 collects from you may be transferred to, and stored at, a country that is not considered to offer an adequate level of protection under your local laws. It may also be processed by staff operating outside your country who work for ARRIVE5 or for one of its suppliers, service providers or partner entities. By submitting your Personal Information, you acknowledge, and, where necessary under local laws, agree to, this transfer, storing or processing. \r\n9.3.ARRIVE5 will take appropriate steps to ensure that your Personal Information is treated securely and in accordance with applicable Privacy Laws and this Privacy Policy. This may include putting in place data transfer agreements or ensuring that ARRIVE5 or third-parties comply with data transfer mechanisms or schemes. \r\n10. JOB APPLICANTS\r\n11. YOUR RIGHTS\r\n11.1. Subject to applicable law, you may have some or all of the following rights in respect of your Personal Information: \r\n11.1.1. Access to own Personal Information: You may have the right to request an overview of your Personal Data in accordance with applicable law. In such a case, where reasonably possible, the overview shall contain information regarding the source (if reasonably available), type, purpose and categories of recipients of the relevant Personal Information. You may also have the right to obtain a copy of the Personal Information held about you. We reserve the right to charge an appropriate administrative fee for such right of access, where permitted by applicable law. \r\n11.1.2. Accuracy and right of rectification: ARRIVE5seeks to ensure that your Personal Data is accurate, complete and kept up-to-date to the extent reasonably necessary for the purposes described herein. If the Personal Information is incorrect, incomplete or not processed in compliance with applicable law or this Policy, you may have the right to have your Personal Information rectified, deleted or blocked (as appropriate) in accordance with applicable law. We reserve the right to charge an appropriate administrative fee for such requests, where permitted by applicable law. \r\n11.1.3. Right to object to or withdraw consent: In certain circumstances (e.g., where your Personal Information is processed on the basis of ARRIVE5’s legitimate interests), you may have the right to object to the processing of your Personal Information on the basis of compelling grounds related to your particular situation and in accordance with applicable law. For instance, you may have the right to request that ARRIVE5 does not process your personal data for marketing purposes. In such a case, you can control your initial marketing preferences by ticking the relevant box situated on the form on which your data is collected (the registration form). Thereafter, you can follow the opt-out links or unsubscribe directions in emails or SMS messages. \r\nIf the processing of your Personal Information is based on your consent, you may have the right to withdraw your consent to such processing at any time. It is worth noting that the withdrawing of consent has no impact on earlier processing on such basis, and does not prevent ARRIVE5 from invoking another legal basis for the processing of the relevant Personal Information. \r\n11.1.4. Right to lodge a complaint: You may have the right to lodge a complaint before the relevant data protection authority or supervisory authority. \r\n11.2. To exercise these rights, where applicable, please contact ARRIVE5 at privacy@ARRIVE5.com or use the appropriate functionality in the App or Site. In this context, ARRIVE5 asks that you provide it with one form of photographic identification so that ARRIVE5 can verify your identity. \r\n11.3. From time to time ARRIVE5 may post links to third-party websites on the Site and/or the App. These links are provided as a courtesy to ARRIVE5\'s users and visitors and are not administered or verified in any way by ARRIVE5. Such links are accessed by you at your own risk and ARRIVE5 makes no representations or warranties about the content of such websites including any cookies used by the website operator. If you follow a link to any of these websites, please note that these websites have their own privacy policies and that ARRIVE5 does not accept any responsibility or liability for these policies. As a result, ARRIVE5 strongly recommends that you read the privacy policies of any third-party websites before you provide any personal data to them. \r\n12. CHILDREN\r\nNeither the Site nor the App are directed or targeted to children under the age of 18, and ARRIVE5 does not knowingly collect personally identifiable information from children under the age of 18. ARRIVE5 requires users under 18 years of age to obtain the consent of a parent or guardian, to view the Site or use the App. If ARRIVE5 learns that a child under 18 years of age has provided personally identifiable information to the Site, it will use reasonable efforts to remove such information from its files. No part of our Service is directed towards children under the age of 18 and we do not want Personal Information from children under the age of 18. If you believe that a child under 18 years of age has given us Personal Information, please contact ARRIVE5 at privacy@ARRIVE5.com. \r\n13. CHANGES TO ARRIVE5\'S PRIVACY POLICY\r\nARRIVE5 may change this Privacy Policy from time to time. Any changes ARRIVE5 may make to this Privacy Policy in the future will be posted on this page and, where ARRIVE5 deems appropriate, notified to you (usually by email). You are therefore invited to check it each time you send ARRIVE5 Personal Information. The date of the most recent revisions will appear on this page. \r\n14. CONTACT\r\nQuestions, comments and requests regarding this Privacy Policy are welcomed and should be addressed to privacy@ARRIVE5.com. \r\nAPPENDIX 1: US\r\nCalifornia Privacy Rights Under California\'s “Shine the Light” law, Cal. Civil Code § 1798.83, California residents have the right to request in writing from businesses with whom they have an established business relationship: (i) a list of the categories of Personal Information, such as name, address, email address, and the type of services provided to that customer, that a business has disclosed to third parties (including affiliates that are separate legal entities) during the immediately preceding calendar year for the third parties\' direct marketing purposes; and (ii) the names and addresses of all such third parties. To request the above information, California residents can email us at privacy@ARRIVE5.com. California residents should include their name, California address, and the nature of their request in their email.\r\n', '1', '2019-10-01 10:34:09');

-- --------------------------------------------------------

--
-- Table structure for table `fare_review`
--

CREATE TABLE `fare_review` (
  `id` int(11) NOT NULL,
  `driver_id` int(11) NOT NULL,
  `fare_review_id` int(11) NOT NULL,
  `review` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `fare_review_question`
--

CREATE TABLE `fare_review_question` (
  `id` int(11) NOT NULL,
  `type` varchar(255) NOT NULL,
  `status` enum('0','1') NOT NULL DEFAULT '1' COMMENT '0 means inactive 1 means active',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `high_paying_zone`
--

CREATE TABLE `high_paying_zone` (
  `id` int(11) NOT NULL,
  `zone_name` varchar(200) COLLATE utf8_unicode_ci DEFAULT '0',
  `latitude1` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `latitude2` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `latitude3` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `latitude4` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `longitude1` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `longitude2` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `longitude3` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `longitude4` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `high_by` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` enum('0','1') COLLATE utf8_unicode_ci NOT NULL COMMENT '0=>Inactive,1=>Active',
  `create_date` datetime DEFAULT NULL,
  `poly` polygon DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `high_paying_zone`
--

INSERT INTO `high_paying_zone` (`id`, `zone_name`, `latitude1`, `latitude2`, `latitude3`, `latitude4`, `longitude1`, `longitude2`, `longitude3`, `longitude4`, `high_by`, `status`, `create_date`, `poly`) VALUES
(1, 'Test', '11', '11', '112', '111', '11', '11', '142', '111', '111', '0', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `makeModel`
--

CREATE TABLE `makeModel` (
  `id` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  `make` varchar(255) NOT NULL,
  `model` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `makeModel`
--

INSERT INTO `makeModel` (`id`, `year`, `make`, `model`) VALUES
(1, 2001, 'Ford', 'Polo'),
(2, 2001, 'Toyota', 'Glanza'),
(3, 2001, 'Suzuki', 'Swift'),
(4, 2001, 'Merecedes', 'Benz'),
(5, 2001, 'Mahindra', 'Thar'),
(6, 2001, 'Hyundai', 'i20'),
(7, 2001, 'Honda', 'CR-V'),
(8, 2001, 'Audi', 'R-8'),
(9, 0, 'Acura', ''),
(10, 0, 'Alfa Romeo\r\n', ''),
(11, 0, 'Aston Martin\r\n', ''),
(12, 0, 'Audi', ''),
(13, 0, 'Bentley', ''),
(14, 0, 'BMW', ''),
(15, 0, 'Bugatti', ''),
(16, 0, 'Buick', ''),
(17, 0, 'Cadillac', ''),
(18, 0, 'Chevrolet', ''),
(19, 0, 'Chrysler', ''),
(20, 0, 'Citroen', ''),
(21, 0, 'Dodge', ''),
(22, 0, 'Ferrari', ''),
(23, 0, 'Fiat', ''),
(24, 0, 'Ford', ''),
(25, 0, 'Geely', ''),
(26, 0, 'GMC', ''),
(27, 0, 'Honda', ''),
(28, 0, 'Hyundai', ''),
(29, 0, 'Infinity\r\n', ''),
(30, 0, 'jaguar', ''),
(31, 0, 'Jeep', ''),
(32, 0, 'Kia', ''),
(33, 0, 'Lamborghini', ''),
(34, 0, 'Land Rover', ''),
(35, 0, 'Lexus', ''),
(36, 0, 'Maserati', ''),
(37, 0, 'Mazda', ''),
(38, 0, 'McLaren', ''),
(39, 0, 'Mercedes-Benz', ''),
(40, 0, 'Mitsubishi', ''),
(41, 0, 'Nissan', ''),
(42, 0, 'Pagani', ''),
(43, 0, 'Peugeot', ''),
(44, 0, 'Porsche', ''),
(45, 0, 'Renault', ''),
(46, 0, 'Rolls Royce', ''),
(47, 0, 'Saab', ''),
(48, 0, 'Subaru', ''),
(49, 0, 'Suzuki', ''),
(50, 0, 'Tata Motors', ''),
(51, 0, 'Tesla', ''),
(52, 0, 'Toyota', ''),
(53, 0, 'Volvo', ''),
(54, 0, 'Skoda', ''),
(55, 0, 'Volkswagen', '');

-- --------------------------------------------------------

--
-- Table structure for table `ngo`
--

CREATE TABLE `ngo` (
  `id` int(222) NOT NULL,
  `name` varchar(255) NOT NULL,
  `title` text NOT NULL,
  `description` longtext NOT NULL,
  `amount` int(5) NOT NULL,
  `account_no` int(25) NOT NULL,
  `address` varchar(255) NOT NULL,
  `added_by` int(11) NOT NULL,
  `modified_by` int(11) NOT NULL,
  `added_on` datetime NOT NULL,
  `modified_on` datetime NOT NULL,
  `status` enum('0','1') NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ngo`
--

INSERT INTO `ngo` (`id`, `name`, `title`, `description`, `amount`, `account_no`, `address`, `added_by`, `modified_by`, `added_on`, `modified_on`, `status`) VALUES
(1, 'Test', 'Test Title', 'Test', 12345, 12345, '', 0, 0, '2019-06-10 11:34:18', '0000-00-00 00:00:00', '1');

-- --------------------------------------------------------

--
-- Table structure for table `notification`
--

CREATE TABLE `notification` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `driver_id` int(11) NOT NULL,
  `booking_id` int(11) NOT NULL,
  `send_to` varchar(255) NOT NULL,
  `notification_msg` varchar(255) NOT NULL,
  `status` enum('0','1') NOT NULL DEFAULT '1' COMMENT '1-active,0-deactive',
  `added_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `payment_trans`
--

CREATE TABLE `payment_trans` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `t_id` varchar(255) NOT NULL,
  `card_number` varchar(255) NOT NULL,
  `card_type` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `promo`
--

CREATE TABLE `promo` (
  `promo_name` varchar(255) NOT NULL,
  `promo_code` varchar(200) NOT NULL,
  `valid_from` datetime NOT NULL,
  `valid_to` datetime NOT NULL,
  `discount` int(4) NOT NULL,
  `added_on` datetime NOT NULL,
  `modified_on` datetime NOT NULL,
  `added_by` int(11) NOT NULL,
  `modified_by` int(11) NOT NULL,
  `user_id` longtext NOT NULL,
  `status` enum('0','1') NOT NULL,
  `id` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `promo_code`
--

CREATE TABLE `promo_code` (
  `id` int(11) NOT NULL,
  `promo_code` varchar(255) NOT NULL,
  `promo_type` enum('1','2','3') NOT NULL DEFAULT '1' COMMENT '1 for flat 2 for percantage 3 for free ride',
  `promo_type_name` varchar(255) NOT NULL,
  `discount` varchar(255) NOT NULL,
  `promo_value` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `maxium_discount` varchar(255) NOT NULL,
  `minimum_purchase_value` varchar(255) NOT NULL,
  `total_used` int(11) NOT NULL,
  `valid_from` date NOT NULL,
  `valid_to` date NOT NULL,
  `status` enum('0','1') NOT NULL DEFAULT '0' COMMENT '0 for inactive 1 for active',
  `added_on` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `last_used` date NOT NULL,
  `updated_on` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `rating`
--

CREATE TABLE `rating` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `driver_id` int(11) NOT NULL,
  `booking_id` int(11) NOT NULL,
  `rating` varchar(255) NOT NULL,
  `review` varchar(255) NOT NULL,
  `rate_for` varchar(255) NOT NULL COMMENT 'user or driver',
  `status` enum('1','0') NOT NULL DEFAULT '1' COMMENT '1 - active, 0 - deactive',
  `added_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `rating_comment_driver`
--

CREATE TABLE `rating_comment_driver` (
  `id` int(11) NOT NULL,
  `comment` varchar(255) NOT NULL,
  `rating` int(11) NOT NULL,
  `review` text NOT NULL,
  `status` enum('1','0') NOT NULL DEFAULT '1' COMMENT '1-active, 0-deactive',
  `added_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `rating_comment_user`
--

CREATE TABLE `rating_comment_user` (
  `id` int(11) NOT NULL,
  `comment` varchar(255) NOT NULL,
  `rating` int(11) NOT NULL,
  `status` enum('1','0') NOT NULL DEFAULT '1' COMMENT '1-active, 0-deactive',
  `added_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `report_reason`
--

CREATE TABLE `report_reason` (
  `id` int(11) NOT NULL,
  `report_reason` varchar(255) NOT NULL,
  `type` varchar(50) NOT NULL,
  `status` enum('0','1') NOT NULL,
  `added_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `report_reason`
--

INSERT INTO `report_reason` (`id`, `report_reason`, `type`, `status`, `added_on`) VALUES
(1, 'demo testing', 'user', '1', '2019-12-27 06:14:36'),
(2, 'hello testing', 'user', '1', '2019-12-27 06:11:37'),
(3, 'hello testing 1', 'user', '1', '2019-12-27 06:11:37'),
(4, 'hello testing 2', 'user', '1', '2019-12-27 06:11:37'),
(5, 'hello testing 3', 'user', '1', '2019-12-27 06:11:37');

-- --------------------------------------------------------

--
-- Table structure for table `review_rating`
--

CREATE TABLE `review_rating` (
  `id` int(11) NOT NULL,
  `driver_id` varchar(255) NOT NULL,
  `booking_id` varchar(255) NOT NULL,
  `rating` varchar(255) NOT NULL,
  `comment` varchar(255) NOT NULL,
  `review` varchar(255) NOT NULL,
  `added_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `review_rating`
--

INSERT INTO `review_rating` (`id`, `driver_id`, `booking_id`, `rating`, `comment`, `review`, `added_on`) VALUES
(3, '1', '2', '2.0', 'sfsdsdfdsf', 'review', '2019-06-26 10:47:43'),
(4, '1', '3', '5.0', 'Dr rd', 'review', '2019-06-26 10:51:59'),
(5, '1', '7', '4.0', 'check this is working now', 'review', '2019-06-26 12:19:23'),
(6, '1', '12', '4.0', 'fbbvdvdebev', 'review', '2019-07-01 05:40:44'),
(7, '1', '13', '5.0', 'uff5dxtf', 'review', '2019-07-01 05:46:38'),
(8, '1', '14', '5.0', 'gegeffevege', 'review', '2019-07-01 12:58:50'),
(9, '1', '19', '3.0', 'cddbb', 'review', '2019-07-08 08:38:23'),
(10, '1', '2', '5.0', 'cucuff', 'review', '2019-07-08 09:25:43'),
(11, '1', '7', '4.0', 'tvrvt', 'review', '2019-07-22 09:54:51'),
(12, '1', '8', '5.0', 'hggf', 'review', '2019-07-22 09:59:47'),
(13, '24', '14', '5.0', 'test blah blah', 'review', '2019-08-05 00:43:17'),
(14, '42', '2', '4.0', 'dveevrve', 'review', '2019-08-05 07:04:04'),
(15, '42', '3', '1.0', '', 'review', '2019-08-05 07:14:43'),
(16, '42', '1', '4.0', 'dvdvv', 'review', '2019-08-12 06:57:03'),
(17, '24', '2', '5.0', '', 'review', '2019-08-13 01:27:52'),
(18, '42', '1', '3.0', 'ok', 'review', '2019-08-13 04:51:43'),
(19, '42', '7', '4.0', 'ok', 'review', '2019-08-13 06:57:52'),
(20, '42', '1', '4.0', 'dgdrvrv', 'review', '2019-08-13 08:46:19'),
(21, '42', '5', '3.0', 'wfecefef', 'review', '2019-08-13 09:28:32'),
(22, '42', '6', '3.0', 'hhhh', 'review', '2019-08-13 09:32:43'),
(23, '42', '1', '5.0', '', 'review', '2019-08-14 12:40:44'),
(24, '55', '4', '1.0', 'test\n', 'review', '2019-08-17 22:38:34'),
(25, '55', '5', '1.0', 'test', 'review', '2019-08-17 22:53:42'),
(26, '42', '1', '4.0', '', 'review', '2019-08-24 06:25:34'),
(27, '42', '1', '1.0', '', 'review', '2019-08-27 05:26:06'),
(28, '42', '2', '5.0', 'seeexx', 'review', '2019-08-27 05:37:01'),
(29, '42', '6', '4.0', 'j bub7h7', 'review', '2019-08-27 05:55:27'),
(30, '42', '9', '4.0', '', 'review', '2019-08-27 06:19:00'),
(31, '42', '10', '4.0', '', 'review', '2019-08-27 06:20:30'),
(32, '42', '22', '4.0', 'fhdyhf', 'review', '2019-08-27 06:34:38'),
(33, '42', '36', '4.0', 'dvevefefefw', 'review', '2019-08-27 07:06:29'),
(34, '42', '37', '4.0', '', 'review', '2019-08-27 07:14:22'),
(35, '42', '1', '4.0', '', 'review', '2019-08-27 07:18:35'),
(36, '42', '2', '5.0', '', 'review', '2019-08-27 07:22:02'),
(37, '42', '3', '4.0', 'dgrvrvege', 'review', '2019-08-27 07:30:25'),
(38, '42', '5', '2.0', '', 'review', '2019-08-27 08:37:28'),
(39, '42', '6', '5.0', '', 'review', '2019-08-27 08:38:35'),
(40, '42', '7', '3.0', '', 'review', '2019-08-27 09:04:26'),
(41, '42', '8', '3.0', '', 'review', '2019-08-27 09:06:51'),
(42, '42', '9', '4.0', '', 'review', '2019-08-27 09:20:42'),
(43, '42', '1', '1.0', '', 'review', '2019-08-27 09:37:56'),
(44, '42', '2', '4.0', '', 'review', '2019-08-27 09:40:05'),
(45, '42', '3', '5.0', 'xbdvdddv', 'review', '2019-08-27 09:41:53'),
(46, '42', '4', '4.0', '', 'review', '2019-08-27 09:43:59'),
(47, '62', '9', '4.0', 'test', 'review', '2019-09-04 15:49:07'),
(48, '2', '1', '5.0', 'good', 'review', '2019-09-17 06:04:17'),
(49, '2', '2', '5.0', 'ecececcc', 'review', '2019-09-17 06:06:15'),
(50, '3', '13', '5.0', '', 'review', '2019-10-04 10:11:03'),
(51, '2', '3', '5.0', '', 'review', '2019-10-16 04:38:47'),
(52, '2', '31', '5.0', '', 'review', '2019-10-16 04:41:01'),
(53, '2', '32', '4.0', '', 'review', '2019-10-16 04:42:30'),
(54, '2', '1', '4.0', 'nice', 'review', '2019-10-16 05:20:23'),
(55, '2', '2', '5.0', '', 'review', '2019-10-16 05:26:43'),
(56, '2', '1', '5.0', 'ok', 'review', '2019-10-16 06:19:58'),
(57, '4', '1', '4.0', 'test\n', 'review', '2019-10-18 23:22:18'),
(58, '4', '3', '1.0', 'test ', 'review', '2019-10-20 15:31:43'),
(59, '6', '21', '5.0', 'great trip\n', 'review', '2019-11-30 17:28:24'),
(60, '2', '39', '4.0', 'hdhufd', 'review', '2019-12-04 09:05:20'),
(61, '2', '47', '3.0', 'vkvj\n', 'review', '2019-12-04 09:53:23'),
(62, '2', '53', '5.0', 'ok', 'review', '2019-12-05 05:15:25'),
(63, '2', '54', '1.0', '', 'review', '2019-12-05 07:07:00'),
(64, '2', '55', '5.0', 'ubbib', 'review', '2019-12-07 04:47:59'),
(65, '2', '80', '4.0', 'fbrvv', 'review', '2020-02-12 11:56:37'),
(66, '16', '100', '3.0', '', 'review', '2020-04-21 11:33:09'),
(67, '2', '3', '1.0', '', 'review', '2020-05-18 10:16:48'),
(68, '2', '13', '1.0', '', 'review', '2020-05-18 10:21:56'),
(69, '2', '12', '1.0', '', 'review', '2020-05-18 10:22:19'),
(70, '2', '14', '1.0', '', 'review', '2020-05-18 10:25:25'),
(71, '2', '15', '1.0', '', 'review', '2020-05-18 12:40:09'),
(72, '16', '46', '1.0', '', 'review', '2020-05-19 07:16:27'),
(73, '19', '5', '1.0', '', 'review', '2020-05-28 06:20:50'),
(74, '3', '235', '5.0', 'test 7', 'review', '2020-08-28 22:32:34'),
(75, '3', '239', '1.0', '', 'review', '2020-08-28 22:58:08');

-- --------------------------------------------------------

--
-- Table structure for table `review_rating_user`
--

CREATE TABLE `review_rating_user` (
  `id` int(11) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `booking_id` varchar(255) NOT NULL,
  `rating` varchar(255) NOT NULL,
  `comment` varchar(255) NOT NULL,
  `review` varchar(255) NOT NULL,
  `added_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `review_rating_user`
--

INSERT INTO `review_rating_user` (`id`, `user_id`, `booking_id`, `rating`, `comment`, `review`, `added_on`) VALUES
(1, '2', '17', '3.0', 'hcitxu utx8tx', 'Review', '2019-06-17 11:29:07'),
(2, '2', '19', '4.0', 'uvucycyc', 'Review', '2019-06-17 11:59:12'),
(3, '2', '21', '3.0', 'ruumnttntntbhhhtth', 'Review', '2019-06-17 12:07:15'),
(4, '2', '21', '1.0', 'tjy6tjtjthj', 'Review', '2019-06-17 12:07:51'),
(5, '2', '2', '4.0', 'nice Ride', 'Review', '2019-06-26 10:40:03'),
(6, '2', '2', '5.0', 'rhrhrhrgrgr', 'Review', '2019-06-26 10:51:25'),
(7, '2', '3', '4.0', 'rgrg4grg3g. ', 'Review', '2019-06-26 10:51:39'),
(8, '2', '3', '4.0', 'rgrg4grg3g. ', 'Review', '2019-06-26 10:51:39'),
(9, '2', '3', '4.0', 'xvdvsvvs', 'Review', '2019-06-26 12:17:38'),
(10, '2', '7', '5.0', 'sgef', 'Review', '2019-06-29 05:26:47'),
(11, '2', '12', '4.0', 'dggegegegeg', 'Review', '2019-07-01 05:41:10'),
(12, '2', '13', '4.0', '', 'Review', '2019-07-01 06:02:57'),
(13, '2', '14', '5.0', 'egegeggg', 'Review', '2019-07-01 12:59:08'),
(14, '2', '19', '1.0', 'Conversation', 'Review', '2019-07-08 08:38:05'),
(15, '2', '2', '3.0', '', 'Review', '2019-07-08 09:25:20'),
(16, '2', '7', '5.0', 'Car smell', 'Review', '2019-07-22 09:56:43'),
(17, '2', '8', '3.0', 'Rush', 'Review', '2019-07-22 10:00:12'),
(18, '11', '14', '3.0', 'Cleanlines', 'Review', '2019-08-05 00:57:11'),
(19, '1', '2', '5.0', 'Cleanlines', 'Review', '2019-08-05 07:03:54'),
(20, '1', '3', '1.0', 'Car smell', 'Review', '2019-08-05 07:14:38'),
(21, '2', '1', '4.0', 'Car smell', 'Review', '2019-08-12 06:56:54'),
(22, '11', '2', '5.0', '', 'Review', '2019-08-13 01:58:33'),
(23, '2', '1', '1.0', 'Conversation', 'Review', '2019-08-13 04:51:34'),
(24, '2', '1', '1.0', 'Navigation', 'Review', '2019-08-13 08:46:11'),
(25, '2', '5', '4.0', 'Navigation', 'Review', '2019-08-13 09:28:33'),
(26, '2', '6', '1.0', 'Other', 'Review', '2019-08-13 09:32:53'),
(27, '2', '1', '1.0', 'Traffic', 'Review', '2019-08-14 12:40:40'),
(28, '11', '4', '1.0', 'Navigation', 'Review', '2019-08-17 22:35:36'),
(29, '2', '2', '4.0', 'Cleanlines', 'Review', '2019-08-27 05:36:53'),
(30, '2', '6', '1.0', 'Conversation', 'Review', '2019-08-27 05:55:32'),
(31, '2', '9', '1.0', 'Music', 'Review', '2019-08-27 06:18:41'),
(32, '2', '10', '1.0', 'Conversation', 'Review', '2019-08-27 06:20:41'),
(33, '2', '22', '1.0', 'Other', 'Review', '2019-08-27 06:34:41'),
(34, '2', '36', '4.0', '', 'Review', '2019-08-27 07:06:47'),
(35, '2', '37', '4.0', 'Cleanlines', 'Review', '2019-08-27 07:14:19'),
(36, '2', '1', '1.0', 'Traffic', 'Review', '2019-08-27 07:18:41'),
(37, '2', '2', '1.0', 'Conversation', 'Review', '2019-08-27 07:28:19'),
(38, '2', '3', '1.0', 'Navigation', 'Review', '2019-08-27 07:31:10'),
(39, '2', '5', '1.0', 'Conversation', 'Review', '2019-08-27 08:36:51'),
(40, '2', '6', '1.0', 'Car smell', 'Review', '2019-08-27 08:38:45'),
(41, '2', '7', '1.0', 'Conversation', 'Review', '2019-08-27 09:04:19'),
(42, '2', '8', '1.0', 'Car smell', 'Review', '2019-08-27 09:07:01'),
(43, '2', '9', '4.0', 'Car smell', 'Review', '2019-08-27 09:21:00'),
(44, '2', '1', '1.0', 'Navigation', 'Review', '2019-08-27 09:37:50'),
(45, '2', '2', '1.0', 'Conversation', 'Review', '2019-08-27 09:40:12'),
(46, '2', '3', '1.0', 'Navigation', 'Review', '2019-08-27 09:42:06'),
(47, '2', '4', '1.0', 'Rush', 'Review', '2019-08-27 09:44:07'),
(48, '3', '1', '5.0', 'Car smell', 'Review', '2019-09-17 06:04:05'),
(49, '3', '2', '5.0', 'Traffic', 'Review', '2019-09-17 06:06:20'),
(50, '3', '1', '4.0', 'Rush', 'Review', '2019-10-16 05:20:31'),
(51, '3', '1', '1.0', 'Conversation', 'Review', '2019-10-16 06:19:59'),
(52, '12', '1', '1.0', 'Rush', 'Review', '2019-11-09 01:49:36'),
(53, '12', '3', '1.0', 'Music', 'Review', '2019-11-09 01:50:54'),
(54, '1', '21', '5.0', 'Music', 'Review', '2019-11-30 17:31:41'),
(55, '3', '39', '4.0', 'Music', 'Review', '2019-12-04 09:05:46'),
(56, '2', '47', '3.0', '', 'Review', '2019-12-04 09:54:04'),
(57, '3', '53', '1.0', 'Music', 'Review', '2019-12-05 05:17:09'),
(58, '3', '55', '1.0', 'Other', 'Review', '2019-12-07 04:47:52'),
(59, '2', '54', '5.0', 'Music', 'Review', '2019-12-25 03:58:59');

-- --------------------------------------------------------

--
-- Table structure for table `split_fare_deatils`
--

CREATE TABLE `split_fare_deatils` (
  `id` int(11) NOT NULL,
  `total_amount` varchar(200) NOT NULL,
  `split_amount` varchar(200) NOT NULL,
  `total_split_users` varchar(200) NOT NULL,
  `share_split_user_id` varchar(200) NOT NULL,
  `user_id` int(11) NOT NULL,
  `number` varchar(200) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `id` int(11) NOT NULL,
  `transaction_id` varchar(100) NOT NULL,
  `bookinng_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `customer_id` varchar(255) NOT NULL,
  `card_id` varchar(255) NOT NULL,
  `stripe_charge_id` varchar(255) NOT NULL,
  `amount` float(10,2) NOT NULL,
  `added_on` datetime NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`id`, `transaction_id`, `bookinng_id`, `user_id`, `customer_id`, `card_id`, `stripe_charge_id`, `amount`, `added_on`) VALUES
(1, '2068LRIJ', 0, 2, '', '', 'ch_1F6vJYJEorJsMEvkeP25D6A3', 1.00, '2019-08-13 08:25:08'),
(2, '7CF1XTIM', 0, 2, '', '', 'ch_1F6vQNJEorJsMEvkyaUGgRjd', 1.00, '2019-08-13 08:32:11'),
(3, 'MJZNTO1Z', 0, 25, '', '', 'ch_1IAtkGAoLq8M1JKevEysHNCQ', 100.00, '2021-01-18 09:09:56'),
(4, '94ESTDIS', 0, 25, '', '', 'ch_1IAwQIAoLq8M1JKeUltPbPNO', 100.00, '2021-01-18 12:01:31'),
(5, '4IGM4JK4', 0, 6, '', '', 'ch_1IAwhKAoLq8M1JKe5IC8gaVk', 100.00, '2021-01-18 12:19:07'),
(6, 'N73P6JX2', 0, 6, '', '', 'ch_1IAwkSAoLq8M1JKe8WumXwzk', 20.00, '2021-01-18 12:22:21'),
(7, 'KTKBU5D4', 0, 6, '', '', 'ch_1IAwmKAoLq8M1JKe4kytHyBQ', 8.00, '2021-01-18 12:24:17');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `stripe_user_id` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `img` varchar(255) NOT NULL,
  `mobile` varchar(255) NOT NULL,
  `appPlatform` varchar(255) NOT NULL,
  `latitude` varchar(255) NOT NULL,
  `longitude` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `fav_music` varchar(255) NOT NULL,
  `about_me` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  `online` enum('1','0') NOT NULL DEFAULT '1' COMMENT '1 - online, 0 - offline ',
  `status` enum('1','0') NOT NULL DEFAULT '1' COMMENT '1 - Active, 0 - Deactive',
  `credit_card_no` varchar(255) NOT NULL,
  `cvv_no` varchar(255) NOT NULL,
  `paypal_email` varchar(255) NOT NULL,
  `paypal_password` varchar(255) NOT NULL,
  `card_valid_month` varchar(255) NOT NULL,
  `card_valid_year` varchar(255) NOT NULL,
  `added_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `invite_code` varchar(255) CHARACTER SET latin1 COLLATE latin1_general_cs NOT NULL,
  `invited_by` varchar(11) NOT NULL,
  `token` varchar(255) NOT NULL,
  `total_points` int(11) NOT NULL,
  `used_point` int(11) NOT NULL,
  `points_available` int(11) NOT NULL,
  `cancelled_point` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `stripe_user_id`, `email`, `first_name`, `last_name`, `password`, `img`, `mobile`, `appPlatform`, `latitude`, `longitude`, `city`, `fav_music`, `about_me`, `code`, `online`, `status`, `credit_card_no`, `cvv_no`, `paypal_email`, `paypal_password`, `card_valid_month`, `card_valid_year`, `added_on`, `invite_code`, `invited_by`, `token`, `total_points`, `used_point`, `points_available`, `cancelled_point`) VALUES
(1, '', 'woso8@hotmail.com', 'James', 'Yated', 'athlone8', '', '+18632589252', 'ios', '28.5039042', '-81.7787967', '', '', '', '6054', '1', '1', '5156121233252558', '123', '', '', '08', '23', '2021-07-05 22:17:29', '51XXW2BE', '', '93283232932098231902130843980jndsjn0208', 0, 0, 0, 0),
(2, '', 'jitu@gmail.com', 'jitu', 'nagar', '123456', '', '+918770513506', 'ios', '', '', '', '', '', '7217', '1', '1', '4242424242424242', '123', '', '', '04', '24', '2021-03-20 11:41:23', 'GH31SZYR', '', 'eZCJLzfYxVw:APA91bE6Fl-nT2uuSBMz5o5zVMQuUL4Byv3rgJFwHPzxKaa5x-8m6YFdsP6nhfYFghZj7xPwhj4T4yjGVchowx4UDa0WjbV1Uhn_aHyJvXDuY4VAZfmWHuhB1hVUlo3DvD8qFB-qDtFT', 0, 0, 0, 0),
(3, '', 'Cristinaalberd1802@gmail.com', 'Shivangi', 'Chourasia ', '1234', '', '+918839074576', 'ios', '', '', '', '', '', '6421', '1', '1', '4242424242424242', '123', '', '', '04', '24', '2021-08-14 04:19:12', 'K7GUT7BG', '', 'dsXcZZkNtnQ:APA91bHT4xBqTvwl6owUHme7ix0Gjx0D1VJSzLAwkNJd5gIBhglmnbgAY7KHXWKyiJ1W85kWBVZfbNFmOXEnhKnUQa91dUymbl2-xc_If06DwLHP0ZeVwVcbZq9L4oY7rYuQ08pqf8np', 0, 0, 0, 0),
(4, '', 'skywinx08@gmail.com', 'skyyy', 'winxxx', '123', '', '+919399517232', 'ios', '', '', '', '', '', '9864', '1', '1', '4242424242424242', '424', '', '', '04', '24', '2021-10-29 13:06:37', '44TRQMCL', '', 'dsXcZZkNtnQ:APA91bHT4xBqTvwl6owUHme7ix0Gjx0D1VJSzLAwkNJd5gIBhglmnbgAY7KHXWKyiJ1W85kWBVZfbNFmOXEnhKnUQa91dUymbl2-xc_If06DwLHP0ZeVwVcbZq9L4oY7rYuQ08pqf8np', 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `users_reason`
--

CREATE TABLE `users_reason` (
  `id` int(11) NOT NULL,
  `type` varchar(255) NOT NULL,
  `booking_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `driver_id` int(11) NOT NULL,
  `reason_id` int(11) NOT NULL,
  `image` varchar(255) NOT NULL,
  `comment` text NOT NULL,
  `added_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users_reason`
--

INSERT INTO `users_reason` (`id`, `type`, `booking_id`, `user_id`, `driver_id`, `reason_id`, `image`, `comment`, `added_on`) VALUES
(1, 'user', 55, 3, 2, 1, '', '', '2019-12-27 06:13:22'),
(2, 'user', 0, 3, 2, 2, '', '', '2019-12-27 07:11:20'),
(3, 'user', 0, 3, 2, 3, '', '', '2019-12-27 07:12:07'),
(4, 'user', 0, 3, 2, 1, '', '', '2019-12-27 07:14:39'),
(5, 'user', 0, 3, 2, 1, '', '', '2019-12-27 07:15:10'),
(6, 'user', 55, 3, 2, 3, '', '', '2019-12-27 07:15:38'),
(7, 'user', 0, 3, 2, 3, '', '', '2019-12-27 07:16:27'),
(8, 'user', 0, 3, 2, 2, '', '', '2019-12-27 07:18:03'),
(9, 'user', 0, 1, 0, 1, '', '', '2020-08-06 20:51:33'),
(10, 'user', 0, 1, 0, 3, '', '', '2020-08-12 20:20:07'),
(11, 'user', 0, 1, 4, 3, '', '', '2020-08-17 16:39:37'),
(12, 'user', 0, 1, 6, 1, '', '', '2020-09-01 19:16:39'),
(13, 'user', 8, 1, 4, 1, '', '', '2020-11-25 18:30:15'),
(14, 'user', 8, 1, 4, 0, '', '', '2020-11-27 23:29:24'),
(15, 'user', 8, 1, 4, 2, '', '', '2020-12-03 03:53:33'),
(16, 'user', 8, 1, 4, 0, '', '', '2020-12-03 21:36:21'),
(17, 'user', 8, 1, 4, 0, '', '', '2020-12-11 22:11:13'),
(18, 'user', 8, 1, 4, 1, '', '', '2021-01-28 21:38:23');

-- --------------------------------------------------------

--
-- Table structure for table `user_donation_detail`
--

CREATE TABLE `user_donation_detail` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `ngo_id` int(11) NOT NULL,
  `donation_amount` int(11) NOT NULL,
  `donation_date` datetime NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_donation_detail`
--

INSERT INTO `user_donation_detail` (`id`, `user_id`, `ngo_id`, `donation_amount`, `donation_date`) VALUES
(1, 3, 1, 12345, '2019-12-24 10:23:20'),
(2, 3, 1, 12345, '2019-12-24 11:30:41'),
(3, 1, 1, 12345, '2021-02-02 13:40:44'),
(4, 3, 1, 12345, '2021-08-17 10:30:31'),
(5, 3, 1, 12345, '2021-08-17 10:30:31');

-- --------------------------------------------------------

--
-- Table structure for table `vehicle_colormaster`
--

CREATE TABLE `vehicle_colormaster` (
  `id` int(11) NOT NULL,
  `color_name` varchar(255) NOT NULL,
  `status` int(11) NOT NULL,
  `added_on` datetime NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `vehicle_colormaster`
--

INSERT INTO `vehicle_colormaster` (`id`, `color_name`, `status`, `added_on`) VALUES
(1, 'Red', 1, '2019-06-11 06:20:00'),
(2, 'Black', 1, '2019-06-11 06:20:00'),
(3, 'White', 1, '2019-06-11 06:20:00'),
(4, 'Silver', 1, '2019-06-11 06:20:00'),
(5, 'Stardust', 1, '2019-06-11 06:20:00'),
(6, 'yellow', 1, '0000-00-00 00:00:00'),
(7, 'purple', 1, '0000-00-00 00:00:00'),
(8, 'Blue', 1, '0000-00-00 00:00:00'),
(9, 'Blue', 1, '0000-00-00 00:00:00'),
(10, 'Brown', 1, '0000-00-00 00:00:00'),
(11, 'Gold', 1, '0000-00-00 00:00:00'),
(12, 'Gray', 1, '0000-00-00 00:00:00'),
(13, 'Burgundy', 1, '0000-00-00 00:00:00'),
(14, 'Tan ', 1, '0000-00-00 00:00:00'),
(15, 'Teal', 1, '0000-00-00 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `vehicle_model`
--

CREATE TABLE `vehicle_model` (
  `id` int(11) NOT NULL,
  `modelname` varchar(255) NOT NULL,
  `make_id` int(222) NOT NULL,
  `status` int(11) NOT NULL,
  `added_on` date NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `vehicle_model`
--

INSERT INTO `vehicle_model` (`id`, `modelname`, `make_id`, `status`, `added_on`) VALUES
(1, 'Glanza', 0, 1, '2019-06-10'),
(2, 'Swift', 0, 1, '2019-06-10'),
(3, 'Honda city', 0, 1, '2019-06-10'),
(4, 'MDX', 9, 1, '0000-00-00'),
(5, 'Giulia Review', 10, 1, '0000-00-00'),
(6, 'RDX', 9, 1, '0000-00-00'),
(7, 'NSX', 9, 1, '0000-00-00'),
(8, 'ILX', 9, 1, '0000-00-00'),
(9, 'RLX', 9, 1, '0000-00-00'),
(10, 'TLX', 9, 1, '0000-00-00'),
(11, 'TLX', 9, 1, '0000-00-00'),
(12, '4C', 10, 1, '0000-00-00'),
(13, '4C Spider', 10, 1, '0000-00-00'),
(14, '\r\n  Giulietta', 10, 1, '0000-00-00'),
(15, 'Mito', 10, 1, '0000-00-00'),
(16, '159', 10, 1, '0000-00-00'),
(17, 'Brera', 10, 1, '0000-00-00'),
(18, 'GT', 10, 1, '0000-00-00'),
(19, '147', 10, 1, '0000-00-00'),
(20, 'Virage', 11, 1, '0000-00-00'),
(21, 'Vanquish', 11, 1, '0000-00-00'),
(22, 'Rapide', 11, 1, '0000-00-00'),
(23, 'Rapide S', 11, 1, '0000-00-00'),
(24, ' DB9', 11, 1, '0000-00-00'),
(25, 'DB9 Volante', 11, 1, '0000-00-00'),
(26, ' DB9 Carbon Edition', 11, 1, '0000-00-00'),
(27, ' Vanquish', 11, 1, '0000-00-00'),
(28, 'Vanquish Volante', 11, 1, '0000-00-00'),
(29, 'Vanquish Carbon Edition', 11, 1, '0000-00-00'),
(30, 'Vanquish Carbon Edition', 11, 1, '0000-00-00'),
(31, 'V8 Vantage', 11, 1, '0000-00-00'),
(32, 'V12 Vantage S', 11, 1, '0000-00-00'),
(33, 'V12 Vantage S Roadster', 11, 1, '0000-00-00'),
(34, 'V8 Vantage Roadster', 11, 1, '0000-00-00'),
(35, 'V8 Vantage S Roadster', 11, 1, '0000-00-00'),
(36, 'Vantage N430', 11, 1, '0000-00-00'),
(37, 'A3', 12, 1, '0000-00-00'),
(38, 'S8', 12, 1, '0000-00-00'),
(39, 'A8', 12, 1, '0000-00-00'),
(40, 'S7', 12, 1, '0000-00-00'),
(41, 'A7', 12, 1, '0000-00-00'),
(42, 'S6', 12, 1, '0000-00-00'),
(43, 'A6', 12, 1, '0000-00-00'),
(44, 'S4', 12, 1, '0000-00-00'),
(45, 'A4', 12, 1, '0000-00-00'),
(46, 'A4', 12, 1, '0000-00-00'),
(47, 'A3', 12, 1, '0000-00-00'),
(48, 'AB L', 12, 1, '0000-00-00'),
(49, 'RS 7', 12, 1, '0000-00-00'),
(50, 'AB L W12', 12, 1, '0000-00-00'),
(51, 'R8', 12, 1, '0000-00-00'),
(52, 'TT', 12, 1, '0000-00-00'),
(53, 'S5', 12, 1, '0000-00-00'),
(54, ' A5', 12, 1, '0000-00-00'),
(55, 'TTS', 12, 1, '0000-00-00'),
(56, 'RS 5', 12, 1, '0000-00-00'),
(57, '1 Series 5-Door', 14, 1, '0000-00-00'),
(58, '1 Series ActiveE', 14, 1, '0000-00-00'),
(59, '3 Series Sedan', 14, 1, '0000-00-00'),
(60, 'ActiveHybrid 3', 14, 1, '0000-00-00'),
(61, '5 Series Sedan\r\n', 14, 1, '0000-00-00'),
(62, '7 Series', 14, 1, '0000-00-00'),
(63, 'ActiveHybrid 7', 14, 1, '0000-00-00'),
(64, 'M3 Sedan', 14, 1, '0000-00-00'),
(65, ' M5 Sedan', 14, 1, '0000-00-00'),
(66, '3 Series Gran Turismo', 14, 1, '0000-00-00'),
(67, '5 Series Gran Turismo', 14, 1, '0000-00-00'),
(68, 'X6', 14, 1, '0000-00-00'),
(69, 'X5', 14, 1, '0000-00-00'),
(70, 'X4', 14, 1, '0000-00-00'),
(71, ' X3', 14, 1, '0000-00-00'),
(72, 'X1', 14, 1, '0000-00-00'),
(73, 'X5 M', 14, 1, '0000-00-00'),
(74, 'X6 M', 14, 1, '0000-00-00'),
(75, ' 3 Series Touring', 14, 1, '0000-00-00'),
(76, '5 Series Touring', 14, 1, '0000-00-00'),
(77, '2 Series Gran Tourer', 14, 1, '0000-00-00'),
(78, '2 Series Active Tourer\r\n', 14, 1, '0000-00-00'),
(79, 'Traverse', 18, 1, '0000-00-00'),
(80, ' Equinox\r\n', 18, 1, '0000-00-00'),
(81, 'Trax', 18, 1, '0000-00-00'),
(82, ' Suburban', 18, 1, '0000-00-00'),
(83, 'Captiva Sport', 18, 1, '0000-00-00'),
(84, 'Tahoe', 18, 1, '0000-00-00'),
(85, 'Suburban', 18, 1, '0000-00-00'),
(86, 'SS', 18, 1, '0000-00-00'),
(87, ' Cruze', 18, 1, '0000-00-00'),
(88, 'Sonic', 18, 1, '0000-00-00'),
(89, 'Spark', 18, 1, '0000-00-00'),
(90, 'Impala', 18, 1, '0000-00-00'),
(91, 'Malibu', 18, 1, '0000-00-00'),
(92, '', 0, 0, '0000-00-00'),
(93, 'Spark EV', 18, 1, '0000-00-00'),
(94, 'Aveo', 18, 1, '0000-00-00'),
(95, 'Explorer', 24, 1, '0000-00-00'),
(96, 'Escape', 24, 1, '0000-00-00'),
(97, 'Edge', 24, 1, '0000-00-00'),
(98, 'Expedition', 24, 1, '0000-00-00'),
(99, 'Flex', 24, 1, '0000-00-00'),
(100, 'Expedition EL', 24, 1, '0000-00-00'),
(101, 'Focus', 24, 1, '0000-00-00'),
(102, 'Fiesta', 24, 1, '0000-00-00'),
(103, 'Taurus', 24, 1, '0000-00-00'),
(104, 'Fusion', 24, 1, '0000-00-00'),
(105, 'Focus ST', 24, 1, '0000-00-00'),
(106, ' C-Max Hybrid', 24, 1, '0000-00-00'),
(107, 'C-Max Energi', 24, 1, '0000-00-00'),
(108, 'Fusion Energi', 24, 1, '0000-00-00'),
(109, 'Fiesta Review', 24, 1, '0000-00-00'),
(110, 'Azera', 6, 1, '0000-00-00'),
(111, 'Equus', 6, 1, '0000-00-00'),
(112, 'Sonata', 6, 1, '0000-00-00'),
(113, 'Genesis', 6, 1, '0000-00-00'),
(114, 'Touring', 6, 1, '0000-00-00'),
(115, 'Elantra GT', 6, 1, '0000-00-00'),
(116, 'Hyundai Sonata Hybrid\r\n', 6, 1, '0000-00-00'),
(117, 'Accent', 6, 1, '0000-00-00'),
(118, 'Veloster', 6, 1, '0000-00-00'),
(119, ' Tiburon', 6, 1, '0000-00-00'),
(120, 'Elantra Coupe', 6, 1, '0000-00-00'),
(121, 'Genesis Coupe', 6, 1, '0000-00-00'),
(122, 'Fit', 7, 1, '0000-00-00'),
(123, 'City', 7, 1, '0000-00-00'),
(124, 'Civic', 7, 1, '0000-00-00'),
(125, ' Fit EV', 7, 1, '0000-00-00'),
(126, ' Accord', 7, 1, '0000-00-00'),
(127, 'Crosstour', 7, 1, '0000-00-00'),
(128, ' FCX Clarity', 7, 1, '0000-00-00'),
(129, 'Ciivc Hybrid', 7, 1, '0000-00-00'),
(130, 'Accord Hybrid', 7, 1, '0000-00-00'),
(131, 'Accord Plug-In', 7, 1, '0000-00-00'),
(132, 'CR-V\r\n', 7, 1, '0000-00-00'),
(133, 'CR-V', 7, 1, '0000-00-00'),
(134, 'Pilot', 7, 1, '0000-00-00'),
(135, ' Odyssey', 7, 1, '0000-00-00'),
(136, 'Element', 7, 1, '0000-00-00'),
(137, 'Passport', 7, 1, '0000-00-00'),
(138, 'S-Class Sedan', 4, 1, '0000-00-00'),
(139, 'C-Class Sedan', 4, 1, '0000-00-00'),
(140, 'E-Class Sedan', 4, 1, '0000-00-00'),
(141, 'E-Class Hybrid', 4, 1, '0000-00-00'),
(142, 'Maybach S600', 4, 1, '0000-00-00'),
(143, 'B-Class Electric Drive', 4, 1, '0000-00-00'),
(144, 'E-Class Wagon', 4, 1, '0000-00-00'),
(145, ' G-Class SUV', 4, 1, '0000-00-00'),
(146, 'GL-Class SUV', 4, 1, '0000-00-00'),
(147, 'M-Class SUV', 4, 1, '0000-00-00'),
(148, 'GLK-Class SUV', 4, 1, '0000-00-00'),
(149, 'GLA-Class SUV', 4, 1, '0000-00-00'),
(150, ' CC', 55, 1, '0000-00-00'),
(151, 'GTI', 55, 1, '0000-00-00'),
(152, ' Eos', 55, 1, '0000-00-00'),
(153, 'Golf', 55, 1, '0000-00-00'),
(154, 'Jetta', 55, 1, '0000-00-00'),
(155, 'Tiguan', 55, 1, '0000-00-00'),
(156, 'Passat', 55, 1, '0000-00-00'),
(157, 'Bettle', 55, 1, '0000-00-00'),
(158, '', 0, 0, '0000-00-00'),
(159, 'Golf R', 55, 1, '0000-00-00'),
(160, ' E-Golf', 55, 1, '0000-00-00'),
(161, 'Touareg', 55, 1, '0000-00-00'),
(162, 'Golf GTI', 55, 1, '0000-00-00'),
(163, 'Jetta Hybrid', 55, 1, '0000-00-00'),
(164, 'Touareg Hybrid', 55, 1, '0000-00-00'),
(165, 'Jetta SportWagen', 55, 1, '0000-00-00');

-- --------------------------------------------------------

--
-- Table structure for table `vehicle_subtype_master`
--

CREATE TABLE `vehicle_subtype_master` (
  `id` int(11) NOT NULL,
  `vehicle_type_id` int(11) NOT NULL,
  `vehicle_model` text COLLATE utf8_unicode_ci NOT NULL,
  `base_price` int(11) NOT NULL,
  `booking_fare` int(11) NOT NULL,
  `minimum_fare` int(11) NOT NULL,
  `charge_per_min` int(11) NOT NULL,
  `charge_per_mile` int(11) NOT NULL,
  `vehicle_capacity` int(11) NOT NULL,
  `vehicle_door` int(11) NOT NULL,
  `active_flag` enum('yes','no') COLLATE utf8_unicode_ci NOT NULL,
  `insertime_mysql` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updatetime_php` datetime NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `vehicle_subtype_master`
--

INSERT INTO `vehicle_subtype_master` (`id`, `vehicle_type_id`, `vehicle_model`, `base_price`, `booking_fare`, `minimum_fare`, `charge_per_min`, `charge_per_mile`, `vehicle_capacity`, `vehicle_door`, `active_flag`, `insertime_mysql`, `updatetime_php`) VALUES
(1, 2, 'Clipper', 20, 22, 15, 20, 21, 5, 4, 'yes', '2019-12-25 10:52:35', '2019-06-11 11:00:00'),
(2, 2, 'Club', 22, 23, 18, 23, 24, 6, 4, 'yes', '2019-12-25 10:52:51', '2019-06-11 11:00:00'),
(3, 1, ' Club Plus', 30, 35, 25, 26, 28, 1000, 2, 'yes', '2019-12-25 10:53:36', '2019-06-11 11:00:00'),
(4, 1, ' Club Plus 1', 30, 35, 25, 26, 28, 1000, 2, 'yes', '2019-12-25 10:53:36', '2019-06-11 11:00:00'),
(5, 1, ' Club Plus 2', 30, 35, 25, 26, 28, 1000, 2, 'yes', '2019-12-25 10:53:36', '2019-06-11 11:00:00'),
(6, 2, 'Club 2', 22, 23, 18, 23, 24, 6, 4, 'yes', '2019-12-25 10:52:51', '2019-06-11 11:00:00'),
(7, 3, 'Club ', 22, 23, 18, 23, 24, 6, 4, 'yes', '2019-12-25 10:52:51', '2019-06-11 11:00:00'),
(8, 3, 'Club 1 ', 22, 23, 18, 23, 24, 6, 4, 'yes', '2019-12-25 10:52:51', '2019-06-11 11:00:00'),
(9, 3, 'Club 2 ', 22, 23, 18, 23, 24, 6, 4, 'yes', '2019-12-25 10:52:51', '2019-06-11 11:00:00'),
(10, 4, 'Club  ', 22, 23, 18, 23, 24, 6, 4, 'yes', '2019-12-25 10:52:51', '2019-06-11 11:00:00'),
(11, 4, 'Club 1  ', 22, 23, 18, 23, 24, 6, 4, 'yes', '2019-12-25 10:52:51', '2019-06-11 11:00:00'),
(12, 4, 'Club 2 ', 22, 23, 18, 23, 24, 6, 4, 'yes', '2019-12-25 10:52:51', '2019-06-11 11:00:00'),
(13, 5, 'Club  ', 22, 23, 18, 23, 24, 6, 4, 'yes', '2019-12-25 10:52:51', '2019-06-11 11:00:00'),
(14, 5, 'Club 1 ', 22, 23, 18, 23, 24, 6, 4, 'yes', '2019-12-25 10:52:51', '2019-06-11 11:00:00'),
(15, 5, 'Club  2', 22, 23, 18, 23, 24, 6, 4, 'yes', '2019-12-25 10:52:51', '2019-06-11 11:00:00'),
(16, 6, 'Club  ', 22, 23, 18, 23, 24, 6, 4, 'yes', '2019-12-25 10:52:51', '2019-06-11 11:00:00'),
(17, 6, 'Club 1  ', 22, 23, 18, 23, 24, 6, 4, 'yes', '2019-12-25 10:52:51', '2019-06-11 11:00:00'),
(18, 6, 'Club  2', 22, 23, 18, 23, 24, 6, 4, 'yes', '2019-12-25 10:52:51', '2019-06-11 11:00:00'),
(19, 7, 'Club  ', 22, 23, 18, 23, 24, 6, 4, 'yes', '2019-12-25 10:52:51', '2019-06-11 11:00:00'),
(20, 7, 'Club 1 ', 22, 23, 18, 23, 24, 6, 4, 'yes', '2019-12-25 10:52:51', '2019-06-11 11:00:00'),
(21, 7, 'Club 2 ', 22, 23, 18, 23, 24, 6, 4, 'yes', '2019-12-25 10:52:51', '2019-06-11 11:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `vehicle_type_master`
--

CREATE TABLE `vehicle_type_master` (
  `id` int(11) NOT NULL,
  `vehicle_type` text COLLATE utf8_unicode_ci NOT NULL,
  `active_flag` enum('yes','no') COLLATE utf8_unicode_ci NOT NULL,
  `inserttime_mysql` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updatetime_php` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `vehicle_type_master`
--

INSERT INTO `vehicle_type_master` (`id`, `vehicle_type`, `active_flag`, `inserttime_mysql`, `updatetime_php`) VALUES
(1, 'Vehicle Class', 'yes', '2019-12-25 10:51:13', '2019-06-27 00:17:00'),
(2, 'Compact', 'yes', '2019-12-25 10:51:20', '2019-06-27 00:17:00'),
(3, 'Economy', 'yes', '2019-12-25 10:51:26', '2019-06-27 00:17:00'),
(4, 'Midsize', 'yes', '2019-12-25 10:51:33', '0000-00-00 00:00:00'),
(5, 'Executive', 'yes', '2019-12-25 10:51:38', '0000-00-00 00:00:00'),
(6, ' SUV', 'yes', '2019-12-25 10:51:46', '0000-00-00 00:00:00'),
(7, 'Minivan', 'yes', '2019-12-07 06:34:25', '0000-00-00 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `zipfarewall`
--

CREATE TABLE `zipfarewall` (
  `id` int(11) NOT NULL,
  `vehicle_model_id` int(11) NOT NULL,
  `zipcode` text COLLATE utf8_unicode_ci NOT NULL,
  `base_price` int(11) NOT NULL,
  `booking_fare` int(11) NOT NULL,
  `minimum_fare` int(11) NOT NULL,
  `charge_per_min` int(11) NOT NULL,
  `charge_per_mile` int(11) NOT NULL,
  `vehicle_capacity` int(11) NOT NULL,
  `vehicle_door` int(11) NOT NULL,
  `active_flag` enum('yes','no') COLLATE utf8_unicode_ci NOT NULL,
  `insertime_mysql` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updatetime_php` datetime NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `add_business_profile`
--
ALTER TABLE `add_business_profile`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `areas`
--
ALTER TABLE `areas`
  ADD PRIMARY KEY (`id`),
  ADD SPATIAL KEY `polygon` (`polygon`);

--
-- Indexes for table `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `booking_driver`
--
ALTER TABLE `booking_driver`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cancel_reason_driver`
--
ALTER TABLE `cancel_reason_driver`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cancel_reason_user`
--
ALTER TABLE `cancel_reason_user`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `driver`
--
ALTER TABLE `driver`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `driver_vechile`
--
ALTER TABLE `driver_vechile`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `driver_vechileimg`
--
ALTER TABLE `driver_vechileimg`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `email_template`
--
ALTER TABLE `email_template`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `faq`
--
ALTER TABLE `faq`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `fare_review`
--
ALTER TABLE `fare_review`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `fare_review_question`
--
ALTER TABLE `fare_review_question`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `high_paying_zone`
--
ALTER TABLE `high_paying_zone`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `makeModel`
--
ALTER TABLE `makeModel`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `ngo`
--
ALTER TABLE `ngo`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `notification`
--
ALTER TABLE `notification`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `payment_trans`
--
ALTER TABLE `payment_trans`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `promo`
--
ALTER TABLE `promo`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `promo_code`
--
ALTER TABLE `promo_code`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `rating`
--
ALTER TABLE `rating`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `rating_comment_driver`
--
ALTER TABLE `rating_comment_driver`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `rating_comment_user`
--
ALTER TABLE `rating_comment_user`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `report_reason`
--
ALTER TABLE `report_reason`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `review_rating`
--
ALTER TABLE `review_rating`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `review_rating_user`
--
ALTER TABLE `review_rating_user`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `split_fare_deatils`
--
ALTER TABLE `split_fare_deatils`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users_reason`
--
ALTER TABLE `users_reason`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_donation_detail`
--
ALTER TABLE `user_donation_detail`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `vehicle_colormaster`
--
ALTER TABLE `vehicle_colormaster`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `vehicle_model`
--
ALTER TABLE `vehicle_model`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `vehicle_subtype_master`
--
ALTER TABLE `vehicle_subtype_master`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `vehicle_type_master`
--
ALTER TABLE `vehicle_type_master`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `zipfarewall`
--
ALTER TABLE `zipfarewall`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `add_business_profile`
--
ALTER TABLE `add_business_profile`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=58;

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `areas`
--
ALTER TABLE `areas`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `booking`
--
ALTER TABLE `booking`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=181;

--
-- AUTO_INCREMENT for table `booking_driver`
--
ALTER TABLE `booking_driver`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=113;

--
-- AUTO_INCREMENT for table `cancel_reason_driver`
--
ALTER TABLE `cancel_reason_driver`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `cancel_reason_user`
--
ALTER TABLE `cancel_reason_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `driver`
--
ALTER TABLE `driver`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `driver_vechile`
--
ALTER TABLE `driver_vechile`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `driver_vechileimg`
--
ALTER TABLE `driver_vechileimg`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `email_template`
--
ALTER TABLE `email_template`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `faq`
--
ALTER TABLE `faq`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `fare_review`
--
ALTER TABLE `fare_review`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `fare_review_question`
--
ALTER TABLE `fare_review_question`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `high_paying_zone`
--
ALTER TABLE `high_paying_zone`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `makeModel`
--
ALTER TABLE `makeModel`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=56;

--
-- AUTO_INCREMENT for table `ngo`
--
ALTER TABLE `ngo`
  MODIFY `id` int(222) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `notification`
--
ALTER TABLE `notification`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `payment_trans`
--
ALTER TABLE `payment_trans`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `promo`
--
ALTER TABLE `promo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `promo_code`
--
ALTER TABLE `promo_code`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `rating`
--
ALTER TABLE `rating`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `rating_comment_driver`
--
ALTER TABLE `rating_comment_driver`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `rating_comment_user`
--
ALTER TABLE `rating_comment_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `report_reason`
--
ALTER TABLE `report_reason`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `review_rating`
--
ALTER TABLE `review_rating`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=76;

--
-- AUTO_INCREMENT for table `review_rating_user`
--
ALTER TABLE `review_rating_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=60;

--
-- AUTO_INCREMENT for table `split_fare_deatils`
--
ALTER TABLE `split_fare_deatils`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `users_reason`
--
ALTER TABLE `users_reason`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `user_donation_detail`
--
ALTER TABLE `user_donation_detail`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `vehicle_colormaster`
--
ALTER TABLE `vehicle_colormaster`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `vehicle_model`
--
ALTER TABLE `vehicle_model`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=166;

--
-- AUTO_INCREMENT for table `vehicle_subtype_master`
--
ALTER TABLE `vehicle_subtype_master`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `vehicle_type_master`
--
ALTER TABLE `vehicle_type_master`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `zipfarewall`
--
ALTER TABLE `zipfarewall`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
