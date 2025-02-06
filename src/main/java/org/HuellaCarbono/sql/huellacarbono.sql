-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.4.32-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para huellacarbono
CREATE DATABASE IF NOT EXISTS `huellacarbono` /*!40100 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci */;
USE `huellacarbono`;

-- Volcando estructura para tabla huellacarbono.actividad
CREATE TABLE IF NOT EXISTS `actividad` (
  `id_actividad` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL DEFAULT '0',
  `id_categoria` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_actividad`),
  KEY `FK_actividad_categoria` (`id_categoria`),
  CONSTRAINT `FK_actividad_categoria` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id_categoria`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla huellacarbono.actividad: ~9 rows (aproximadamente)
INSERT INTO `actividad` (`id_actividad`, `nombre`, `id_categoria`) VALUES
	(1, 'Conducir coche', 1),
	(2, 'Usar transporte público', 1),
	(3, 'Viajar en avión', 1),
	(4, 'Consumo eléctrico', 2),
	(5, 'Consumo de gas', 2),
	(6, 'Comer carne de res', 3),
	(7, 'Comer alimentos vegetarianos', 3),
	(8, 'Generar residuos domésticos', 4),
	(9, 'Consumo de agua potable', 5);

-- Volcando estructura para tabla huellacarbono.categoria
CREATE TABLE IF NOT EXISTS `categoria` (
  `id_categoria` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL DEFAULT '0',
  `factor_emision` varchar(50) NOT NULL DEFAULT '0',
  `unidad` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_categoria`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla huellacarbono.categoria: ~5 rows (aproximadamente)
INSERT INTO `categoria` (`id_categoria`, `nombre`, `factor_emision`, `unidad`) VALUES
	(1, 'Transporte', '0.21', 'Km'),
	(2, 'Energía', '0.233', 'KWh'),
	(3, 'Alimentación', '2.5', 'Kg'),
	(4, 'Residuos', '0.41', 'Kg'),
	(5, 'Agua', '0.35', 'm³');

-- Volcando estructura para tabla huellacarbono.habito
CREATE TABLE IF NOT EXISTS `habito` (
  `id_usuario` int(11) NOT NULL,
  `id_actividad` int(11) NOT NULL,
  `fecuencia` varchar(50) DEFAULT NULL,
  `tipo` varchar(50) DEFAULT NULL,
  `ultima_fecha` date DEFAULT NULL,
  PRIMARY KEY (`id_usuario`,`id_actividad`),
  KEY `FK_habito_actividad` (`id_actividad`),
  KEY `FK_habito_usuario` (`id_usuario`),
  CONSTRAINT `FK_habito_actividad` FOREIGN KEY (`id_actividad`) REFERENCES `actividad` (`id_actividad`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_habito_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla huellacarbono.habito: ~0 rows (aproximadamente)
INSERT INTO `habito` (`id_usuario`, `id_actividad`, `fecuencia`, `tipo`, `ultima_fecha`) VALUES
	(5, 1, '2', 'semanal', '2025-02-11');

-- Volcando estructura para tabla huellacarbono.huella
CREATE TABLE IF NOT EXISTS `huella` (
  `id_registro` int(11) NOT NULL AUTO_INCREMENT,
  `valor` int(11) DEFAULT NULL,
  `unidad` varchar(50) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `id_actividad` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_registro`),
  KEY `FK_huella_actividad` (`id_actividad`),
  KEY `FK_huella_usuario` (`id_usuario`),
  CONSTRAINT `FK_huella_actividad` FOREIGN KEY (`id_actividad`) REFERENCES `actividad` (`id_actividad`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_huella_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla huellacarbono.huella: ~3 rows (aproximadamente)
INSERT INTO `huella` (`id_registro`, `valor`, `unidad`, `fecha`, `id_usuario`, `id_actividad`) VALUES
	(17, 100, 'Km', '2025-01-24', 5, 1),
	(18, 2, 'Kg', '2025-01-24', 5, 6),
	(19, 20, 'KWh', '2025-02-04', 5, 5);

-- Volcando estructura para tabla huellacarbono.recomendacion
CREATE TABLE IF NOT EXISTS `recomendacion` (
  `id_recomendacion` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(50) DEFAULT NULL,
  `impacto_estimado` varchar(50) DEFAULT NULL,
  `id_categoria` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_recomendacion`),
  KEY `FK_recomendacion_categoria` (`id_categoria`),
  CONSTRAINT `FK_recomendacion_categoria` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id_categoria`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla huellacarbono.recomendacion: ~10 rows (aproximadamente)
INSERT INTO `recomendacion` (`id_recomendacion`, `descripcion`, `impacto_estimado`, `id_categoria`) VALUES
	(1, 'Usa bicicleta o camina en distancias cortas', '30', 1),
	(2, 'Opta por el transporte público en vez del coche', '45', 1),
	(3, 'Compartir coche con compañeros reduce emisiones', '20', 1),
	(4, 'Apaga dispositivos eléctricos cuando no los uses', '10', 2),
	(5, 'Usa bombillas LED en lugar de incandescentes', '15', 2),
	(6, 'Reduce el consumo de carne de res y opta por veget', '5', 3),
	(7, 'Compra productos locales y de temporada', '10', 3),
	(8, 'Recicla residuos para disminuir emisiones', '5', 4),
	(9, 'Reduce el uso de plásticos desechables', '10', 4),
	(10, 'Reduce el tiempo de ducha y ahorra agua', '15', 5);

-- Volcando estructura para tabla huellacarbono.usuario
CREATE TABLE IF NOT EXISTS `usuario` (
  `id_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `contrasena` varchar(64) DEFAULT NULL,
  `fecha_registro` date DEFAULT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla huellacarbono.usuario: ~2 rows (aproximadamente)
INSERT INTO `usuario` (`id_usuario`, `nombre`, `email`, `contrasena`, `fecha_registro`) VALUES
	(5, 'Perico', 'perico@gmail.com', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', '2025-02-06'),
	(6, 'Pepillo', 'pepillo@gmail.com', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', '2025-02-06');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
