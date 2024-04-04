CREATE TABLE account 
(                                                   
    id SERIAL PRIMARY KEY,                                                          
    name VARCHAR(50) NOT NULL,                                                      
    email VARCHAR(50) NOT NULL,                                                 
    password VARCHAR(50) NOT NULL,                                                  
    image BYTEA                                                                     
);

CREATE TABLE posts (                                                   
  id SERIAL PRIMARY KEY, 
  title varchar(50) NOT NULL,                                                     
image BYTEA                                                                   
);

CREATE TABLE reviews (
    id SERIAL PRIMARY KEY,
    post_id bigint,
    user_id bigint,
    rating varchar(1) NOT NULL,
    title varchar(50) NOT NULL,
    description varchar(1000) NOT NULL,
    date date NOT NULL DEFAULT current_date
);