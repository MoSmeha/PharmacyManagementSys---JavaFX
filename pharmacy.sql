-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Dec 15, 2023 at 04:42 PM
-- Server version: 8.0.31
-- PHP Version: 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pharmacy`
--

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
CREATE TABLE IF NOT EXISTS `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `type` varchar(100) NOT NULL,
  `medicine_id` int NOT NULL,
  `brand` varchar(100) NOT NULL,
  `productName` varchar(100) NOT NULL,
  `quantity` int NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`id`, `customer_id`, `type`, `medicine_id`, `brand`, `productName`, `quantity`, `price`) VALUES
(1, 1, 'Antibiotics', 1, 'brand', 'product', 2, 100),
(2, 2, 'Losartan', 2, 'brand2', 'name2', 3, 60),
(3, 2, 'Antibiotics', 1, 'brand', 'product', 5, 250),
(4, 2, 'Antibiotics', 1, 'brand', 'product', 5, 250),
(5, 3, 'Antibiotics', 1, 'brand', 'product', 2, 100),
(6, 4, 'Antibiotics', 1, 'brand', 'product', 2, 100),
(7, 5, 'Antibiotics', 1, 'brand', 'product', 2, 100),
(8, 6, 'Antibiotics', 1, 'brand', 'product', 2, 100),
(9, 7, 'Losartan', 2, 'brand2', 'name2', 2, 40),
(10, 8, 'Antibiotics', 1, 'brand', 'product', 2, 100),
(11, 9, 'Antibiotics', 1, 'brand', 'product', 3, 150),
(12, 10, 'Antibiotics', 1, 'brand', 'product', 2, 100),
(13, 11, 'Antibiotics', 1, 'brand', 'product', 3, 150),
(14, 12, 'Antibiotics', 1, 'brand', 'product', 2, 100),
(15, 13, 'Losartan', 2, 'brand2', 'name2', 3, 60),
(16, 14, 'Antibiotics', 1, 'brand', 'product', 2, 100),
(17, 15, 'Albuterol', 3, 'brand3', 'product4', 2, 60),
(18, 16, 'Albuterol', 3, 'brand3', 'product4', 1, 30),
(19, 17, 'Antibiotics', 4, 'brand4', 'prod4', 2, 100),
(20, 18, 'Losartan', 3, 'brand3', 'product3', 2, 60),
(21, 19, 'Antibiotics', 1, 'brand1', 'product1', 2, 100),
(22, 20, 'Metformin', 2, 'brand2', 'product2', 2, 80),
(23, 21, 'Metformin', 6, 'proddd', 'name6', 2, 80),
(24, 22, 'Antibiotics', 1, 'brand1', 'product1', 2, 100),
(25, 22, 'Losartan', 3, 'brand3', 'product3', 3, 90),
(26, 23, 'Antibiotics', 1, 'brand1', 'product1', 5, 250);

-- --------------------------------------------------------

--
-- Table structure for table `customer_info`
--

DROP TABLE IF EXISTS `customer_info`;
CREATE TABLE IF NOT EXISTS `customer_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `total` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `customer_info`
--

INSERT INTO `customer_info` (`id`, `customer_id`, `total`) VALUES
(1, 1, 100),
(2, 2, 560),
(3, 3, 100),
(4, 4, 100),
(5, 5, 100),
(6, 6, 100),
(7, 7, 40),
(8, 8, 100),
(9, 9, 150),
(10, 10, 100),
(11, 11, 150),
(12, 12, 100),
(13, 13, 60),
(14, 14, 100),
(15, 15, 60),
(16, 16, 30),
(17, 17, 100),
(18, 18, 60),
(19, 19, 100),
(20, 20, 80),
(21, 21, 80),
(23, 22, 190);

-- --------------------------------------------------------

--
-- Table structure for table `medicine`
--

DROP TABLE IF EXISTS `medicine`;
CREATE TABLE IF NOT EXISTS `medicine` (
  `id` int NOT NULL AUTO_INCREMENT,
  `medicine_id` int NOT NULL,
  `brand` varchar(100) NOT NULL,
  `productName` varchar(100) NOT NULL,
  `type` varchar(100) NOT NULL,
  `status` varchar(100) NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `medicine`
--

INSERT INTO `medicine` (`id`, `medicine_id`, `brand`, `productName`, `type`, `status`, `price`) VALUES
(1, 1, 'brand1', 'product1', 'Antibiotics', 'Available', 50),
(3, 3, 'brand3', 'product3', 'Losartan', 'Available', 30),
(5, 4, 'brand4', 'prod4', 'Albuterol', 'Available', 20),
(6, 5, 'productV', 'namee', 'Hydrocodone', 'Available', 10),
(9, 6, 'brand', 'producttt', 'Metformin', 'Available', 50);

-- --------------------------------------------------------

--
-- Table structure for table `notes`
--

DROP TABLE IF EXISTS `notes`;
CREATE TABLE IF NOT EXISTS `notes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `note_content` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `notes`
--

INSERT INTO `notes` (`id`, `note_content`) VALUES
(1, 'write'),
(2, 'i forgot to add medicine'),
(3, 'notes'),
(4, ', i forgot to stock up'),
(5, 'nour'),
(6, 'write');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `phone` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `phone`) VALUES
(1, 'user', 'project', 66666),
(2, 'Username', 'prog3', 71123456);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
