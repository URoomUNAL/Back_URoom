insert into rule (id, name)
select * from (select 1, 'No se permite fumar') as tmp
where not exists (
        select name from rule where name = 'No se permite fumar'
) limit 1;

insert into rule (id, name)
select * from (select 2, 'No se permiten mascotas') as tmp
where not exists (
        select name from rule where name = 'No se permiten mascotas'
) limit 1;

insert into rule (id, name)
select * from (select 3, 'No hay un horario de llegada flexible') as tmp
where not exists (
        select name from rule where name = 'No hay un horario de llegada flexible'
) limit 1;

insert into rule (id, name)
select * from (select 4, 'No se permiten visitas') as tmp
where not exists (
        select name from rule where name = 'No se permiten visitas'
) limit 1;

insert into service (id, name )
select * from (select 1, 'Lavadora' ) as tmp
where not exists (
        select name from service where name = 'Lavadora'
) limit 1;

insert into service (id, name )
select * from (select 2, 'Amoblada' ) as tmp
where not exists (
        select name from service where name = 'Amoblada'
) limit 1;

insert into service (id, name )
select * from (select 3, 'Nevera' ) as tmp
where not exists (
        select name from service where name = 'Nevera'
) limit 1;

insert into service (id, name )
select * from (select 4, 'Internet' ) as tmp
where not exists (
        select name from service where name = 'Internet'
) limit 1;

insert into service (id, name )
select * from (select 5, 'Televisión' ) as tmp
where not exists (
        select name from service where name = 'Televisión'
    ) limit 1;

insert into service (id, name )
select * from (select 6, 'Servicios incluídos' ) as tmp
where not exists (
        select name from service where name = 'Servicios incluídos'
    ) limit 1;

insert into service (id, name )
select * from (select 7, 'Baño privado' ) as tmp
where not exists (
        select name from service where name = 'Baño privado'
    ) limit 1;

insert into service (id, name )
select * from (select 8, 'Cocina' ) as tmp
where not exists (
        select name from service where name = 'Cocina'
    ) limit 1;

insert into service (id, name )
select * from (select 9, 'Vista exterior' ) as tmp
where not exists (
        select name from service where name = 'Vista exterior'
    ) limit 1;

insert into service (id, name )
select * from (select 10, 'Gym' ) as tmp
where not exists (
        select name from service where name = 'Gym'
    ) limit 1;

insert into service (id, name )
select * from (select 11, 'Piscina' ) as tmp
where not exists (
        select name from service where name = 'Piscina'
    ) limit 1;

insert into service (id, name )
select * from (select 12, 'Parqueadero' ) as tmp
where not exists (
        select name from service where name = 'Baño privado'
    ) limit 1;

insert into service (id, name )
select * from (select 13, 'Agua caliente' ) as tmp
where not exists (
        select name from service where name = 'Agua caliente'
    ) limit 1;