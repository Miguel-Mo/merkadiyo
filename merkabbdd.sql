CREATE DATABASE  IF NOT EXISTS `merkabbdd` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `merkabbdd`;
-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: merkabbdd
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cantidad_producto_compra`
--

DROP TABLE IF EXISTS `cantidad_producto_compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cantidad_producto_compra` (
  `producto_idproducto` int NOT NULL,
  `compra_id_compra` int NOT NULL,
  `cantidad` int NOT NULL,
  PRIMARY KEY (`producto_idproducto`,`compra_id_compra`),
  KEY `fk_producto_has_compra_compra1_idx` (`compra_id_compra`),
  KEY `fk_producto_has_compra_producto_idx` (`producto_idproducto`),
  CONSTRAINT `fk_producto_has_compra_compra1` FOREIGN KEY (`compra_id_compra`) REFERENCES `compra` (`id_compra`),
  CONSTRAINT `fk_producto_has_compra_producto` FOREIGN KEY (`producto_idproducto`) REFERENCES `producto` (`idproducto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cantidad_producto_compra`
--

LOCK TABLES `cantidad_producto_compra` WRITE;
/*!40000 ALTER TABLE `cantidad_producto_compra` DISABLE KEYS */;
INSERT INTO `cantidad_producto_compra` VALUES (1,2,2),(1,5,1),(2,1,1),(2,12,1),(2,13,1),(3,6,3),(4,3,1),(4,8,2),(4,10,1),(5,7,5),(5,9,2),(5,11,1);
/*!40000 ALTER TABLE `cantidad_producto_compra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compra`
--

DROP TABLE IF EXISTS `compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `compra` (
  `id_compra` int NOT NULL AUTO_INCREMENT,
  `fecha_compra` date NOT NULL,
  `empleado_id_empleado` int NOT NULL,
  PRIMARY KEY (`id_compra`),
  KEY `fk_compra_empleado1_idx` (`empleado_id_empleado`),
  CONSTRAINT `fk_compra_empleado1` FOREIGN KEY (`empleado_id_empleado`) REFERENCES `empleado` (`id_empleado`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compra`
--

LOCK TABLES `compra` WRITE;
/*!40000 ALTER TABLE `compra` DISABLE KEYS */;
INSERT INTO `compra` VALUES (1,'2021-02-23',2),(2,'2021-02-23',2),(3,'2021-02-23',2),(4,'2021-02-23',2),(5,'2021-02-23',2),(6,'2021-02-23',1),(7,'2021-02-23',1),(8,'2021-02-24',2),(9,'2021-02-24',2),(10,'2021-02-24',1),(11,'2021-02-24',2),(12,'2021-02-24',1),(13,'2021-02-24',2);
/*!40000 ALTER TABLE `compra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empleado`
--

DROP TABLE IF EXISTS `empleado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empleado` (
  `id_empleado` int NOT NULL AUTO_INCREMENT,
  `ultima_sesion` datetime DEFAULT NULL,
  `fecha_contratacion` date NOT NULL,
  PRIMARY KEY (`id_empleado`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empleado`
--

LOCK TABLES `empleado` WRITE;
/*!40000 ALTER TABLE `empleado` DISABLE KEYS */;
INSERT INTO `empleado` VALUES (1,'2021-02-24 13:05:28','2021-02-20'),(2,'2021-02-24 13:06:04','2021-02-18');
/*!40000 ALTER TABLE `empleado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto` (
  `idproducto` int NOT NULL AUTO_INCREMENT,
  `nombre_producto` varchar(45) NOT NULL,
  `precio_venta` float NOT NULL,
  `precio_proveedor` float NOT NULL,
  `cantidad_stock` int NOT NULL,
  PRIMARY KEY (`idproducto`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES (1,'galleta',1,0.5,0),(2,'donete',1,0.6,15),(3,'baguette',0.5,0.1,22),(4,'napolitana',2,0.9,19),(5,'bizcocho',5,2,22);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'merkabbdd'
--

--
-- Dumping routines for database 'merkabbdd'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-24 13:09:40
