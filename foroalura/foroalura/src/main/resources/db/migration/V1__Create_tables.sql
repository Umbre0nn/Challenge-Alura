CREATE TABLE curso (
                       id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                       nombre VARCHAR(255) NOT NULL,
                       categoria VARCHAR(255) NOT NULL
);

CREATE TABLE perfil (
                        id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                        nombre VARCHAR(255) NOT NULL
);

CREATE TABLE usuario (
                         id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                         nombre VARCHAR(255) NOT NULL,
                         correo_electronico VARCHAR(255) NOT NULL,
                         contrasena VARCHAR(255) NOT NULL
);

CREATE TABLE topico (
                        id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                        titulo VARCHAR(255) NOT NULL,
                        mensaje TEXT NOT NULL,
                        fecha_creacion TIMESTAMP NOT NULL,
                        status VARCHAR(255) NOT NULL,
                        autor_id BIGINT REFERENCES usuario(id),
                        curso_id BIGINT REFERENCES curso(id)
);

CREATE TABLE respuesta (
                           id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                           mensaje TEXT NOT NULL,
                           fecha_creacion TIMESTAMP NOT NULL,
                           autor_id BIGINT REFERENCES usuario(id),
                           topico_id BIGINT REFERENCES topico(id),
                           solucion BOOLEAN DEFAULT FALSE
);

CREATE TABLE usuario_perfil (
                                usuario_id BIGINT REFERENCES usuario(id),
                                perfil_id BIGINT REFERENCES perfil(id),
                                PRIMARY KEY (usuario_id, perfil_id)
);
