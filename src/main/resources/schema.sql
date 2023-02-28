CREATE TABLE IF NOT EXISTS public.customers
(
    id integer NOT NULL,
    dob date NOT NULL,
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    first_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    note character varying(255) COLLATE pg_catalog."default",
    phone character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT customers_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.facilities
(
    id integer NOT NULL,
    color character varying(10) COLLATE pg_catalog."default",
    description character varying(255) COLLATE pg_catalog."default" NOT NULL,
    duration integer,
    fee double precision,
    title character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT facilities_pkey PRIMARY KEY (id),
    CONSTRAINT facilities_duration_check CHECK (duration >= 0)
);

CREATE TABLE IF NOT EXISTS public.appointments
(
    id bigint NOT NULL,
    appointment_date_time timestamp with time zone NOT NULL,
    color character varying(10) COLLATE pg_catalog."default",
    duration integer,
    status character varying(255) COLLATE pg_catalog."default" NOT NULL,
    customer_id integer NOT NULL,
    facility_id integer NOT NULL,
    CONSTRAINT appointments_pkey PRIMARY KEY (id),
    CONSTRAINT fkeky0q40sp61iolhm5c129o8ti FOREIGN KEY (facility_id)
        REFERENCES public.facilities (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkrlbb09f329sfsmftrh7y0yxtk FOREIGN KEY (customer_id)
        REFERENCES public.customers (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT appointments_duration_check CHECK (duration >= 0)
);