CREATE table users (

    id TEXT PRIMARY KEY UNIQUE NOT FULL,
    login TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    role TEXT NOT NULL

)