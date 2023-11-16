CREATE TABLE `Person` (
  `id` integer PRIMARY KEY,
  `name` varchar(255),
  `firstname` varchar(255),
  `birthdate` date
);

CREATE TABLE `Manager` (
  `id` integer PRIMARY KEY,
  `address` varchar(255),
  `person_id` integer,
  `log_id` integer
);

CREATE TABLE `Medecin` (
  `id` integer PRIMARY KEY,
  `address` varchar(255),
  `health_professional_number` integer,
  `intern` integer,
  `person_id` integer,
  `log_id` integer
);

CREATE TABLE `Patient` (
  `id` integer PRIMARY KEY,
  `identifier` integer,
  `demande_visite_specialisee` integer,
  `demande_suivi_medical` integer,
  `person_id` integer
);

CREATE TABLE `Log` (
  `id` integer PRIMARY KEY,
  `login` varchar(255),
  `password` varchar(255)
);

CREATE TABLE `Creneau` (
  `id` integer PRIMARY KEY,
  `startHour` timestamp
);

CREATE TABLE `RendezVous` (
  `id` integer PRIMARY KEY,
  `patient_id` integer,
  `creneau_id` integer,
  `salle_numero` integer
);

CREATE TABLE `Salle` (
  `numero` integer PRIMARY KEY,
  `nom` varchar(255)
);

ALTER TABLE `Log` ADD FOREIGN KEY (`id`) REFERENCES `Manager` (`log_id`);

ALTER TABLE `Person` ADD FOREIGN KEY (`id`) REFERENCES `Manager` (`person_id`);

ALTER TABLE `Log` ADD FOREIGN KEY (`id`) REFERENCES `Medecin` (`log_id`);

ALTER TABLE `Person` ADD FOREIGN KEY (`id`) REFERENCES `Medecin` (`person_id`);

ALTER TABLE `Person` ADD FOREIGN KEY (`id`) REFERENCES `Patient` (`person_id`);

ALTER TABLE `Patient` ADD FOREIGN KEY (`id`) REFERENCES `RendezVous` (`patient_id`);

ALTER TABLE `Creneau` ADD FOREIGN KEY (`id`) REFERENCES `RendezVous` (`creneau_id`);

ALTER TABLE `Salle` ADD FOREIGN KEY (`numero`) REFERENCES `RendezVous` (`salle_numero`);
