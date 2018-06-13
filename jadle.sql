--
-- PostgreSQL database dump
--

-- Dumped from database version 10.2
-- Dumped by pg_dump version 10.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: foodtypes; Type: TABLE; Schema: public; Owner: Guest
--

CREATE TABLE foodtypes (
    id integer NOT NULL,
    name character varying
);


ALTER TABLE foodtypes OWNER TO "Guest";

--
-- Name: foodtypes_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE foodtypes_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE foodtypes_id_seq OWNER TO "Guest";

--
-- Name: foodtypes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE foodtypes_id_seq OWNED BY foodtypes.id;


--
-- Name: restaurants; Type: TABLE; Schema: public; Owner: Guest
--

CREATE TABLE restaurants (
    id integer NOT NULL,
    name character varying,
    address character varying,
    zipcode character varying,
    phone character varying,
    website character varying,
    email character varying
);


ALTER TABLE restaurants OWNER TO "Guest";

--
-- Name: restaurants_foodtypes; Type: TABLE; Schema: public; Owner: Guest
--

CREATE TABLE restaurants_foodtypes (
    id integer NOT NULL,
    foodtypeid integer,
    restaurantid integer
);


ALTER TABLE restaurants_foodtypes OWNER TO "Guest";

--
-- Name: restaurants_foodtypes_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE restaurants_foodtypes_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE restaurants_foodtypes_id_seq OWNER TO "Guest";

--
-- Name: restaurants_foodtypes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE restaurants_foodtypes_id_seq OWNED BY restaurants_foodtypes.id;


--
-- Name: restaurants_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE restaurants_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE restaurants_id_seq OWNER TO "Guest";

--
-- Name: restaurants_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE restaurants_id_seq OWNED BY restaurants.id;


--
-- Name: reviews; Type: TABLE; Schema: public; Owner: Guest
--

CREATE TABLE reviews (
    id integer NOT NULL,
    writtenby character varying,
    rating character varying,
    content character varying,
    restaurantid integer,
    createdat bigint
);


ALTER TABLE reviews OWNER TO "Guest";

--
-- Name: reviews_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE reviews_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE reviews_id_seq OWNER TO "Guest";

--
-- Name: reviews_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE reviews_id_seq OWNED BY reviews.id;


--
-- Name: foodtypes id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY foodtypes ALTER COLUMN id SET DEFAULT nextval('foodtypes_id_seq'::regclass);


--
-- Name: restaurants id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY restaurants ALTER COLUMN id SET DEFAULT nextval('restaurants_id_seq'::regclass);


--
-- Name: restaurants_foodtypes id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY restaurants_foodtypes ALTER COLUMN id SET DEFAULT nextval('restaurants_foodtypes_id_seq'::regclass);


--
-- Name: reviews id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY reviews ALTER COLUMN id SET DEFAULT nextval('reviews_id_seq'::regclass);


--
-- Data for Name: foodtypes; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY foodtypes (id, name) FROM stdin;
\.


--
-- Data for Name: restaurants; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY restaurants (id, name, address, zipcode, phone, website, email) FROM stdin;
\.


--
-- Data for Name: restaurants_foodtypes; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY restaurants_foodtypes (id, foodtypeid, restaurantid) FROM stdin;
\.


--
-- Data for Name: reviews; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY reviews (id, writtenby, rating, content, restaurantid, createdat) FROM stdin;
\.


--
-- Name: foodtypes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('foodtypes_id_seq', 1, false);


--
-- Name: restaurants_foodtypes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('restaurants_foodtypes_id_seq', 1, false);


--
-- Name: restaurants_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('restaurants_id_seq', 1, false);


--
-- Name: reviews_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('reviews_id_seq', 1, false);


--
-- Name: foodtypes foodtypes_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY foodtypes
    ADD CONSTRAINT foodtypes_pkey PRIMARY KEY (id);


--
-- Name: restaurants_foodtypes restaurants_foodtypes_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY restaurants_foodtypes
    ADD CONSTRAINT restaurants_foodtypes_pkey PRIMARY KEY (id);


--
-- Name: restaurants restaurants_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY restaurants
    ADD CONSTRAINT restaurants_pkey PRIMARY KEY (id);


--
-- Name: reviews reviews_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY reviews
    ADD CONSTRAINT reviews_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

