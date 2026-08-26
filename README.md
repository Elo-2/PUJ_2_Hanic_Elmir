# Life Management System

Desktop aplikacija razvijena u **Java programskom jeziku** pomoću **Java Swing** biblioteke i **MongoDB** baze podataka.

Cilj aplikacije je omogućiti korisniku da na jednom mjestu prati i organizuje različite oblasti svakodnevnog života, kao što su finansije, navike, spavanje, zadaci i raspoloženje.

## Funkcionalnosti

Aplikacija omogućava:

* Registraciju korisnika
* Prijavu korisnika
* Čuvanje podataka za svakog korisnika posebno
* Praćenje finansija
* Praćenje navika
* Praćenje spavanja
* Upravljanje zadacima
* Praćenje raspoloženja
* Pregled statistike i analitike

## Moduli

### 1. Finance Tracker

Omogućava korisniku:

* Dodavanje prihoda
* Dodavanje rashoda
* Odabir kategorije
* Dodavanje opisa
* Izmjenu transakcije
* Brisanje transakcije
* Pregled ukupnih prihoda
* Pregled ukupnih rashoda
* Pregled trenutnog salda
* Izvoz finansijskih podataka

### 2. Habit Tracker

Omogućava:

* Dodavanje novih navika
* Dodavanje opisa i kategorije
* Evidenciju završenih dana
* Praćenje uzastopnih dana
* Brisanje navika
* Pregled procenta izvršenosti
* Osnovnu analitiku navika

### 3. Sleep Tracker

Omogućava korisniku da evidentira:

* Datum spavanja
* Broj sati spavanja
* Kvalitet spavanja
* Napomene

Dostupna je i analitika koja prikazuje:

* Prosječan broj sati spavanja
* Prosjek spavanja u posljednjih 7 dana
* Najčešću kvalitetu spavanja
* Ukupan broj evidentiranih noći
* Broj noći sa dobrim spavanjem

### 4. Task Planner

Omogućava:

* Dodavanje zadataka
* Dodavanje opisa
* Postavljanje prioriteta
* Postavljanje statusa zadatka
* Postavljanje roka
* Kategorisanje zadataka
* Brisanje zadataka
* Označavanje zadatka kao završenog

Analitika prikazuje:

* Ukupan broj zadataka
* Broj završenih zadataka
* Procenat završenih zadataka
* Broj zadataka koji su u toku
* Broj prekoračenih zadataka
* Raspodjelu zadataka prema prioritetu

### 5. Mood Tracker

Omogućava korisniku da evidentira:

* Trenutno raspoloženje
* Datum
* Napomenu

Raspoloženja su predstavljena pomoću kategorija:

* 😀 Odlično
* 😊 Dobro
* 😐 Normalno
* 😔 Loše
* 😡 Veoma loše

Aplikacija omogućava i izračun prosječnog raspoloženja korisnika.

### 6. Analytics

Analytics modul objedinjuje podatke iz različitih modula i omogućava pregled osnovnih statistika korisnika.

Primjeri statistika:

* Ukupni prihodi
* Ukupni rashodi
* Finansijski saldo
* Uspješnost navika
* Završeni zadaci
* Prosječno raspoloženje

## Tehnologije

Projekat koristi:

* **Java**
* **Java Swing**
* **MongoDB**
* **MongoDB Atlas**
* **MongoDB Java Driver**
* **IntelliJ IDEA**
* **Git**
* **GitHub**

## Struktura projekta

```text
src/
└── lifemanagement/
    ├── AnalyticsForm.java
    ├── AnalyticsForm.form
    ├── FinanceTrackerForm.java
    ├── FinanceTrackerForm.form
    ├── Habit.java
    ├── HabitForm.java
    ├── HabitForm.form
    ├── HabitManager.java
    ├── LoginRegisterForm.java
    ├── MainMenuForm.java
    ├── MainMenuForm.form
    ├── MongoDBConnection.java
    ├── Mood.java
    ├── MoodForm.java
    ├── MoodForm.form
    ├── MoodManager.java
    ├── Sleep.java
    ├── SleepForm.java
    ├── SleepForm.form
    ├── SleepManager.java
    ├── Task.java
    ├── TaskForm.java
    ├── TaskForm.form
    ├── TaskManager.java
    ├── Transaction.java
    ├── TransactionManager.java
    ├── User.java
    └── UserManager.java
```

## MongoDB

Aplikacija koristi MongoDB za trajno čuvanje podataka.

Podaci su organizovani kroz različite kolekcije, uključujući:

```text
users
life_transactions
habits
sleep
tasks
moods
```

Svaki korisnik ima svoj `userId`, na osnovu kojeg se podaci filtriraju i prikazuju samo za trenutno prijavljenog korisnika.

## Prijava i registracija

Prilikom registracije korisnik unosi osnovne podatke:

* Korisničko ime
* Lozinku
* E-mail
* Ime
* Prezime

Nakon uspješne prijave korisnik dobija pristup glavnom meniju aplikacije.

## Glavni meni

Glavni meni predstavlja centralnu tačku aplikacije i omogućava pristup modulima:

```text
Life Management System

Dobrodošao, [ime korisnika]

[Finance Tracker]
[Habit Tracker]
[Sleep Tracker]
[Task Planner]
[Mood Tracker]
[Analytics]
```

## Cilj projekta

Cilj projekta je demonstrirati razvoj desktop aplikacije koja koristi objektno-orijentisano programiranje, grafički korisnički interfejs i bazu podataka.

Projekat objedinjuje više funkcionalnosti u jedan sistem za upravljanje svakodnevnim aktivnostima i omogućava korisniku pregled vlastitih podataka i osnovne statistike.

## Autor

**Life Management System**


