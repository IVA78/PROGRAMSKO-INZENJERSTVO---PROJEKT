-- Create the 'korisnik' table
CREATE TABLE korisnik (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          korisnicko_ime VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL,
                          lozinka VARCHAR(255) NOT NULL,
                          tip_korisnika VARCHAR(13) NOT NULL,
                          adresa VARCHAR(255),
                          placanje_clanarine BOOLEAN
);

-- Create the 'dogadjanje' table
CREATE TABLE dogadjanje (
                            id_dogadjanja BIGINT AUTO_INCREMENT PRIMARY KEY,
                            naziv_dogadjanja VARCHAR(255) NOT NULL,
                            tip_dogadjanja VARCHAR(255) NOT NULL,
                            lokacija_dogadjanja VARCHAR(255) NOT NULL,
                            vrijeme_dogadjanja TIMESTAMP,
                            trajanje DOUBLE PRECISION,
                            organizator_id BIGINT,
                            cijena_ulaznice DOUBLE PRECISION,
                            FOREIGN KEY (organizator_id) REFERENCES korisnik(id)
);
-- Create the 'poveznica' table
CREATE TABLE poveznica (
                           id_poveznice BIGINT AUTO_INCREMENT PRIMARY KEY,
                           organizator_id BIGINT,
                           link VARCHAR(255),
                           FOREIGN KEY (organizator_id) REFERENCES korisnik(id)
);

-- Create the 'medijski_sadrzaj' table
CREATE TABLE medijski_sadrzaj (
                                  id_medijskog_sadrzaja BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  adresa_medijskog_sadrzaja VARCHAR(255),
                                  id_dogadjanja BIGINT,
                                  FOREIGN KEY (id_dogadjanja) REFERENCES dogadjanje(id_dogadjanja)
);

-- Create the 'recenzija' table
CREATE TABLE recenzija (
                           id_rezenczije BIGINT AUTO_INCREMENT PRIMARY KEY,
                           recenzija_tekst TEXT,
                           ocjena INT,
                           id_dogadjanja BIGINT,
                           id_korisnik BIGINT,
                           FOREIGN KEY (id_dogadjanja) REFERENCES dogadjanje(id_dogadjanja),
                           FOREIGN KEY (id_korisnik) REFERENCES korisnik(id)
);

-- Create the 'dolazak_korisnika' table
CREATE TABLE dolazak_korisnika (
                                   id_dolaska_korisnika BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   status_dolaska VARCHAR(255),
                                   id_dogadjanja BIGINT,
                                   id_korisnik BIGINT,
                                   FOREIGN KEY (id_dogadjanja) REFERENCES dogadjanje(id_dogadjanja),
                                   FOREIGN KEY (id_korisnik) REFERENCES korisnik(id)
);


