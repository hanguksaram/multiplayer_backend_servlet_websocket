# multiplayer_backend_servlet_websocket
неполный функионал можно посмотреть на https://immense-wildwood-80974.herokuapp.com/ (функциональность обрезана тк
сокет соединение не создается на ws (проблемы с ssl)вероятно настроки томката хероку)


ИНСТРУКЦИИ ЛОКАЛЬНЫЙ ДЕПЛОЙ:
1. выполнить sql script на локальной бд (конекшн стринг захадкожен в warе)


SET FOREIGN_KEY_CHECKS = 0;


CREATE SCHEMA IF NOT EXISTS `test01` ;

CREATE USER 'newuser'@'localhost' IDENTIFIED BY 'password';                       |
GRANT ALL PRIVILEGES ON `test01`.* TO 'newuser'@'localhost';  

CREATE TABLE IF NOT EXISTS `test01`.`Characters` (
  `User_ID` INT(11) NOT NULL,
  `Rating` INT(11) NULL DEFAULT NULL,
  `DamageMultiplier` INT(11) NULL DEFAULT NULL,
  `Health` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`User_ID`),
  CONSTRAINT `'Fk_User_ID'`
    FOREIGN KEY (`User_ID`)
    REFERENCES `test02`.`Users` (`User_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


CREATE TABLE IF NOT EXISTS `test01`.`Users` (
  `User_ID` INT(11) NOT NULL AUTO_INCREMENT,
  `Login` VARCHAR(45) NOT NULL,
  `Password` VARCHAR(512) NOT NULL,
  PRIMARY KEY (`User_ID`),
  UNIQUE INDEX `Login_UNIQUE` (`Login` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 18
DEFAULT CHARACTER SET = utf8;
SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------------------------------------------------------

2. деплой deploy/unnamed.war на локальном инстансе tomcata(ver 8-9)
