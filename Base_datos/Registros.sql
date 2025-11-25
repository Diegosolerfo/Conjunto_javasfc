INSERT INTO USUARIO(CEDULA,NOMBRE,APELLIDO,CLAVE,CORREO,TIPO_USUARIO,ESTADO,TELEFONO,GENERO,FECHA_NACIMIENTO)
VALUES 
(1147484290,'Diego Alejandro','Soler Fonseca','soler123','diego.soler.admin@gmail.com','ADMINISTRADOR','ACTIVO',3125565319,'MASCULINO','2005-05-16'),
(1034785120,'Daniel Felipe','Chica Feria','Chica.admin','daniel.chica.admin@gmail.com','ADMINISTRADOR','ACTIVO',3109876543,'MASCULINO','2005-05-16'),
(4521876531,'Juan Camilo','Giraldo Leon','leon.admin','juan.camilo@gmail.com','ADMINISTRADOR','ACTIVO',3152345678,'MASCULINO','2005-05-16'),
(1234587642,'Fernando Andres','Villegas Vargas','superdeveloper','fernando.villegas@gmail.com','CAJERO','ACTIVO',3001234567,'MASCULINO','2005-05-16'),
(5464243457,'Camilo Jose','Ferney Zuñiga','camilo.cliente','camilo.ferney@gmail.com','CAJERO','ACTIVO',3178765432,'MASCULINO','2005-05-16'),
(6456424414,'Manuel Alexander','Castañeda Molina','manuel.cliente','alexander.castaneda@gmail.com','CLIENTE','ACTIVO',3125565319,'MASCULINO','2005-05-16'),
(7897823146,'Jefferson Gustavo','Diaz Gimenez','jefferson.cliente','gustavo.diaz@gmail.com','CLIENTE','ACTIVO',3146543210,'MASCULINO','2005-05-16'),
(1546723649,'Camila Andrea','Doris Nunez','camila.cliente','andrea.doris@gmail.com','CLIENTE','ACTIVO',3009876543,'FEMENINO','2005-05-16'),
(7842136614,'Paula Sofia','Zapata Arjona','paula.cliente','sofia.zapata@gmail.com','CLIENTE','ACTIVO',3112345678,'FEMENINO','2005-05-16'),
(7895432112,'Keinner Jose','Nubia Cifuentes','keinner.cliente','jose.cifuentes@gmail.com','CLIENTE','ACTIVO',3187654321,'MASCULINO','2005-05-16'),
(1114567896,'Emanuel Camilo','Gimenez Lopez','emmanuel.cliente','camilo.gimenez@gmail.com','CLIENTE','ACTIVO',3018765432,'MASCULINO','2005-05-16'),
(1235789641,'Daniela Denis','Cortes Marulanda','daniela.cliente','daniela.cortes@gmail.com','CLIENTE','ACTIVO',3176543210,'FEMENINO','2005-05-16'),
(1012345678,'Valentina','Ramirez Torres','valentina.cliente','valentina.ramirez@gmail.com','CLIENTE','ACTIVO',3123456789,'FEMENINO','1998-04-15'),
(1023456789,'Santiago','Lopez Herrera','santiago.cliente','santiago.lopez@gmail.com','CLIENTE','ACTIVO',3108765432,'MASCULINO','1987-11-02'),
(1034567890,'Laura','Martinez Peña','laura.cliente','laura.martinez@gmail.com','CLIENTE','ACTIVO',3156781234,'FEMENINO','1995-06-22'),
(1045678901,'Andres','Rodriguez Salazar','andres.cliente','andres.rodriguez@gmail.com','CLIENTE','ACTIVO',3134567890,'MASCULINO','1984-01-10'),
(1056789012,'Maria Camila','Gonzalez Rivera','camila.cliente','camila.gonzalez@gmail.com','CLIENTE','ACTIVO',3165432187,'FEMENINO','1990-08-05'),
(1067890123,'Jorge','Castro Medina','jorge.cliente','jorge.castro@gmail.com','CLIENTE','ACTIVO',3147896543,'MASCULINO','1982-12-18'),
(1078901234,'Juliana','Ramírez Páez','juliana.cliente','juliana.paez@gmail.com','CLIENTE','ACTIVO',3009873214,'FEMENINO','1999-03-11'),
(1089012345,'Felipe','Moreno Cárdenas','felipe.cliente','felipe.moreno@gmail.com','CLIENTE','ACTIVO',3117654321,'MASCULINO','2000-09-07'),
(1090123456,'Isabella','Pineda Gómez','isabella.cliente','isabella.pineda@gmail.com','CLIENTE','ACTIVO',3176543123,'FEMENINO','1994-02-28'),
(1101234567,'Mateo','Cano Restrepo','mateo.cliente','mateo.cano@gmail.com','CLIENTE','ACTIVO',3187650987,'MASCULINO','1986-07-19'),
(1122345678,'Sofia','Mendoza Becerra','sofia.cliente','sofia.mendoza@gmail.com','CLIENTE','ACTIVO',3120987654,'FEMENINO','1997-10-13'),
(1133456789,'Carlos','Torres Andrade','carlos.cliente','carlos.torres@gmail.com','CLIENTE','ACTIVO',3098765432,'MASCULINO','1980-05-25'),
(1144567890,'Karen','Velasquez Jimenez','karen.cliente','karen.velasquez@gmail.com','CLIENTE','ACTIVO',3143216789,'FEMENINO','1989-01-16'),
(1155678901,'Sebastian','Diaz Pardo','sebastian.cliente','sebastian.diaz@gmail.com','CLIENTE','ACTIVO',3156547890,'MASCULINO','2001-11-09'),
(1166789012,'Luisa Fernanda','Reyes Romero','luisa.cliente','luisa.reyes@gmail.com','CLIENTE','ACTIVO',3112349876,'FEMENINO','1993-06-29'),
(1177890123,'David','Vargas Niño','david.cliente','david.vargas@gmail.com','CLIENTE','ACTIVO',3123456780,'MASCULINO','1985-09-21'),
(1188901234,'Angela','Suarez Delgado','angela.cliente','angela.suarez@gmail.com','CLIENTE','ACTIVO',3104567891,'FEMENINO','1996-12-04'),
(1199012345,'Miguel Angel','Ortega Cuellar','miguel.cliente','miguel.ortega@gmail.com','CLIENTE','ACTIVO',3132109876,'MASCULINO','1991-03-30');
INSERT INTO PRODUCTO(NOMBRE_PRODUCTO, DESCRIPCION_PRODUCTO, VALOR_UNITARIO, UNIDAD_MEDIDA, ESTADO_PRODUCTO) VALUES
('Arroz Diana', 'Arroz blanco de grano largo', 2500, '500g', 'ACTIVO'),
('Aceite Premier', 'Aceite vegetal para cocina', 7000, '1000ml', 'ACTIVO'),
('Huevos AA', 'Cubeta de huevos frescos', 14000, '30 und', 'ACTIVO'),
('Leche Alquería', 'Leche entera en bolsa', 3800, '1100ml', 'ACTIVO'),
('Pan Bimbo', 'Pan tajado blanco', 5500, '500g', 'ACTIVO'),
('Queso costeño', 'Queso salado fresco', 12000, '500g', 'ACTIVO'),
('Gaseosa Coca-Cola', 'Bebida gaseosa sabor cola', 2500, '400ml', 'ACTIVO'),
('Gaseosa Colombiana', 'Bebida gaseosa sabor kola', 2500, '400ml', 'ACTIVO'),
('Cerveza Poker', 'Cerveza rubia nacional', 2800, '330ml', 'ACTIVO'),
('Detergente Ariel', 'Detergente en polvo para ropa', 8500, '1kg', 'ACTIVO'),
('Jabón Rey', 'Jabón azul para lavar ropa', 3000, '250g', 'ACTIVO'),
('Crema dental Colgate', 'Crema dental triple acción', 4500, '75ml', 'ACTIVO'),
('Shampoo Savital', 'Shampoo con sábila', 6500, '550ml', 'ACTIVO'),
('Arepas La Antioqueña', 'Arepas de maíz precocidas', 5000, '10 und', 'ACTIVO'),
('Chocorramo', 'Ponqué cubierto de chocolate', 2500, '65g', 'ACTIVO'),
('Papas Margarita', 'Papas fritas sabor natural', 2500, '30g', 'ACTIVO'),
('Bon Bon Bum', 'Bombón sabor fresa', 300, '1 und', 'ACTIVO'),
('Sal Refisal', 'Sal refinada para consumo', 1200, '500g', 'ACTIVO'),
('Azúcar Manuelita', 'Azúcar blanca refinada', 2800, '500g', 'ACTIVO'),
('Café Águila Roja', 'Café molido tradicional', 4500, '250g', 'ACTIVO');

INSERT INTO PROVEEDOR(NOMBRE_PROVEEDOR,NUMERO_TELEFONICO,CORREO_PROVEEDOR,DIRECCION,ESTADO) VALUES
('Productos Ramo S.A.','6012345678','contacto@ramo.com.co','Av. Calle 68 # 24-15, Bogotá, Cundinamarca','ACTIVO'),
('Diana Corporación S.A.S.','6023456789','info@dianacorp.com','Calle 26 # 82-48, Bogotá, Cundinamarca','ACTIVO'),
('Colanta Cooperativa','6045678901','servicio@colanta.com','Cl. 10 # 22-30, Medellín, Antioquia','ACTIVO'),
('Compañía Envasadora del Atlántico (CEA)','6056789012','info@cea.com.co','Zona Franca, Barranquilla, Atlántico','ACTIVO'),
('Atlantic Fs S.A.S.','6057890123','ventas@atlanticfs.com.co','Cra. 50 # 80-60, Barranquilla, Atlántico','ACTIVO');
INSERT INTO MOVIMIENTO (VALOR_MOVIMIENTO, FECHA_MOVIMIENTO, OBSERVACIONES, DESCUENTO, TIPO_MOVIMIENTO, ESTADO_MOVIMIENTO, CLIENTE) VALUES
(175000, '2025-07-10', 'Cliente pago puntual', 8, 'COMPRA', 'REALIZADO', 1067890123),
(92000,  '2025-07-11', 'Cliente fue grosero en la tienda', NULL, 'VENTA', 'REALIZADO', 1078901234),
(132000, '2025-07-12', 'Cliente no pago', 6, 'DEUDA', 'PENDIENTE', 1089012345),
(185000, '2025-07-13', 'Cliente hizo reclamo por precio', 12, 'COMPRA', 'REALIZADO', 1090123456),
(48000,  '2025-07-14', 'Cliente dejo deuda pendiente', NULL, 'DEUDA', 'PENDIENTE', 1101234567),
(117000, '2025-07-15', 'Cliente pago incompleto', 5, 'VENTA', 'REALIZADO', 1114567896),
(143500, '2025-07-16', 'Cliente solicito fiado', 10, 'COMPRA', 'REALIZADO', 1122345678);
INSERT INTO DETALLE_COMPRA(ID_COMPRA, CANTIDAD_COMPRADA, PROVEEDOR, MOVIMIENTO, PRODUCTO_COMPRADO) VALUES
(3,40,2,6,6),
(4,35,3,6,7),
(5,25,2,6,8),
(6,20,4,9,9),
(7,50,5,9,10),
(8,15,3,10,11),
(9,30,6,10,12),
(10,45,1,10,13);
INSERT INTO DETALLE_VENTA(ID_VENTA, CANTIDAD_VENDIDA, MOVIMIENTO, PRODUCTO_VENDIDO) VALUES
(2,10,7,14),
(3,25,7,15),
(4,18,8,16);
INSERT INTO DETALLE_DEUDAS(ID_DEUDA, SALDO, CANTIDAD_DEUDA, FECHA_VENCIMIENTO, MOVIMIENTO, PRODUCTO_PENDIENTE) VALUES
(3,126000,3,'2025-08-12',8,17),
(4,46000,1,'2025-08-14',9,18);
