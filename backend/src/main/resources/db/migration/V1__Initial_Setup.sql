create table vehicule(
    id bigserial primary key,
    matricule text not null unique,
    marque text,
    carburant text,
    type text,
    annee_circulation int

)