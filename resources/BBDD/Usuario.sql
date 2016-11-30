CREATE TABLE `facu_schema`.`usuario` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `apellido` VARCHAR(45) NOT NULL,
  `usuario` VARCHAR(15) NOT NULL,
  `password` VARCHAR(16) NOT NULL,
  `rol` VARCHAR(10) NOT NULL,
  `activo` INT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `usuario_UNIQUE` (`usuario` ASC));
