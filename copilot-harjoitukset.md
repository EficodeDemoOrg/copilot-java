# GitHub Copilot -harjoitukset: Yksinkertainen sääsovellus komentoriville (päivitetty)

Tämä dokumentti sisältää sarjan harjoituksia, joiden avulla opit käyttämään GitHub Copilotin ominaisuuksia yksinkertaisen Java-pohjaisen sääsovelluksen yhteydessä. Käymme läpi koodipohjan tutkimista, uusien ominaisuuksien ideointia ja niiden toteuttamista Copilotin avulla.

**Tärkeimmät Copilotin käyttöliittymäkohdat:**

* **Chat-näkymä:** Käytetään kysymysten esittämiseen, koodin/testien/dokumentaation generointiin ja toimintojen käynnistämiseen. Tilat kuten "Ask" (oletus), "Edits" ja "Agent" voi valita chat-näkymän valikosta.
* **Inline Chat:** Nopea keskustelu suoraan editorissa (Oletus: `Cmd+I` / `Ctrl+I`), usein nopeisiin selityksiin tai valitun koodin muokkaukseen. Mahdollistaa useiden ehdotusten selaamisen pikanäppäimillä (esim. `Alt+]`/`Option+]` tai katso komentopaletti "Copilot: View Next/Previous Suggestion").
* **Osallistujat (`@`-viittaukset):** Tuovat laajan kontekstin keskusteluun, kuten koko työtilan (`@workspace`) tai VS Code -ympäristön (`@vscode`). **Tärkeä rajoitus:** Vain **yksi osallistuja** per pyyntö (esim. `@workspace` TAI `@vscode`).
* **Muuttujat (`#`-viittaukset):** Tarkentavat Copilotin kontekstia (esim. tiedostot `#file`, valinnat `#selection`, symbolit `#sym`, symbolin käytöt/määrittelyt `#usage`, muutokset `#changes`, koodipohjan rakenne `#codebase`, web-sisältö `#fetch`, viimeinen terminaalikomento `#terminalLastCommand`, terminaalivalinta `#terminalSelection`). Muuttujia *voi* yhdistää osallistujaan (esim. `@workspace #file:JokuTiedosto.java`).
    * **Interaktiivinen valinta:** Tiedostoille, kansioille, symboleille (`#sym`) ja käyttöhaulle (`#usage`) kirjoita `#` ja ala kirjoittaa nimeä; VS Code ehdottaa työtilan kohteita (esim. `#OpenWe` ehdottaa `OpenWeatherMapClient.java`-tiedostoa ja -luokkaa).
    * **Raahaa ja pudota:** Voit myös raahata tiedostoja/kansioita suoraan Explorerista chatin syötekenttään.
* **Slash-komennot:** Ohjaavat Copilotin toimintaa chatissa tai inline chatissa (esim. `/explain`, `/tests`, `/fix`, `/new`).
* **Koodin täydennys:** Automaattiset ehdotukset kirjoittaessa.
* **Mukautetut ohjeet:** Tiedostot kuten `.github/copilot-instructions.md` ohjaavat Copilotin ehdotuksia työtilassa.

**Huomio `@workspace` vs `#codebase` ja osallistujien käytöstä:**

Sekä `@workspace` että `#codebase` antavat Copilotille kontekstin koko projektista/työtilasta, mutta käyttötarkoitus vaihtelee:
* `@workspace` on **osallistuja** yleisiin projektikysymyksiin, yleensä "Ask"-tilassa. Vain yksi osallistuja per pyyntö.
* `#codebase` on **muuttuja**, joka viittaa myös koko työtilaan. Sitä käytetään erityisesti "Edits"- tai "Agent"-tiloissa (`/new`), joissa tarvitaan laajaa analyysiä. Koska se on muuttuja, sitä voi yhdistää osallistujaan (esim. `@vscode` ja `#codebase`).

Näissä harjoituksissa käytetään yleensä `@workspace` laajoihin kysymyksiin ja `#codebase` kun tarvitaan laajaa kontekstia generointiin/muokkaukseen. Kokeile, mikä toimii parhaiten omassa tilanteessasi.

**Edellytykset:**

* Visual Studio Code asennettuna.
* GitHub Copilot ja Copilot Chat -laajennukset asennettu ja konfiguroitu.
* Simple Weather CLI -projekti avattuna VS Codessa.
* Integroitu terminaali auki (esim. Näytä > Terminaali).
* Perustiedot Javasta ja Mavenista.
* OpenWeatherMap API -avain asetettu ympäristömuuttujaan `OPENWEATHERMAP_API_KEY` (vaaditaan joissain vaiheissa).

---

## Osa 1: Koodipohjan ja ympäristön tutkiminen

**Tavoite:** Käytä Copilot Chatia eri kontekstimuuttujien kanssa (`@workspace`, `#file`, `#folder`, `#sym`, `#usage`, `#fetch`, `#terminalLastCommand`, `#terminalSelection`, `@vscode`) saadaksesi nopeasti käsityksen projektista, sen riippuvuuksista, komponenttien suhteista, kehitysympäristöstä ja ulkoisesta tiedosta.

---

### Harjoitus 1.1: Projektin yleiskatsaus (`@workspace`, `/explain`)

* **Tarkoitus:** Ymmärtää projektin päätavoite, pääkomponentit ja rakenne laajalla työtilakontekstilla.
* **Tavoite:** Harjoittele `@workspace`-osallistujan käyttöä Copilot Chatissa "Ask"-tilassa.
* **Vaiheet:**
    1.  Avaa Copilot Chat -näkymä VS Codessa. Varmista, että tila on "Ask".
    2.  Kirjoita chatin syötekenttään:
        ```
        @workspace /explain Mikä on tämän projektin päätarkoitus ja miten se on rakennettu? Mitkä ovat keskeiset Java-luokat lähdekoodin ja README:n perusteella?
        ```
    3.  Tarkastele Copilotin selitystä.

### Harjoitus 1.2: Yksittäisen luokan ymmärtäminen (`#` tiedostoviittaus, `/explain`)

* **Tarkoitus:** Syventyä yhden luokan toimintaan.
* **Tavoite:** Harjoittele tiedostoviittausta `#`-etuliitteellä ja interaktiivisella valinnalla.
* **Vaiheet:**
    1.  Avaa Copilot Chat -näkymä.
    2.  Kirjoita `#` syötekenttään.
    3.  Ala kirjoittaa `OpenWeatherMapClient`. VS Code ehdottaa tiedostoja ja symboleja. Valitse *tiedosto* `src/main/java/com/weather/app/OpenWeatherMapClient.java`.
    4.  Lisää perään komento `/explain Selitä tämän luokan rooli. Miten se hakee tietoa OpenWeatherMap API:sta? Mitä riippuvuuksia sillä näyttää olevan?` ja paina Enter.
    5.  Analysoi vastaus.
    6.  *(Vaihtoehto)* Kokeile raahata `OpenWeatherMapClient.java`-tiedosto Explorerista chatin syötekenttään.

### Harjoitus 1.3: Riippuvuuksien selittäminen (`#` tiedostoviittaus, `/explain`)

* **Tarkoitus:** Ymmärtää ulkoiset kirjastot.
* **Tavoite:** Harjoittele `pom.xml`-viittausta `#`-etuliitteellä.
* **Vaiheet:**
    1.  Avaa Copilot Chat -näkymä.
    2.  Kirjoita `#` ja ala kirjoittaa `pom`. Valitse `pom.xml`.
    3.  Lisää `/explain Selitä tässä listattujen pääriippuvuuksien roolit, kuten Jackson, JUnit, Mockito ja mahdolliset Maven-pluginien kuten Surefire tai Assembly merkitys.` ja paina Enter.
    4.  Tarkastele selitystä.

### Harjoitus 1.4: Dokumentaation generointi (`#selection`)

* **Tarkoitus:** Generoida dokumentaatiota automaattisesti.
* **Tavoite:** Harjoittele `#selection`-muuttujan käyttöä editorin sisällölle.
* **Vaiheet:**
    1.  Avaa tiedosto `src/main/java/com/weather/app/WeatherService.java`.
    2.  Etsi ja valitse koko `getWeather(String city)`-metodin allekirjoitus ja runko.
    3.  Avaa Copilot Chat -näkymä.
    4.  Varmista, että tila on "Ask".
    5.  Kirjoita:
        ```
        #selection Generoi Javadoc-valmistelu valitulle metodille. Selitä sen tarkoitus, parametrit, palautusarvo ja mahdolliset poikkeukset koodin perusteella.
        ```
    6.  Copilot antaa Javadocin. Tarkista ja kopioi koodiin tarvittaessa.

### Harjoitus 1.5: Kansion sisällön tutkiminen (`#` kansioviittaus, `/explain`)

* **Tarkoitus:** Saada yhteenveto kansion koodista.
* **Tavoite:** Harjoittele kansion viittausta `#`-etuliitteellä.
* **Vaiheet:**
    1.  Avaa Copilot Chat -näkymä.
    2.  Kirjoita `#` ja ala kirjoittaa `com/weather/app`. Valitse *kansio* `src/main/java/com/weather/app`.
    3.  Lisää `/explain Yhteenveto tämän sovelluspaketin Java-luokkien tarkoituksesta.` ja paina Enter.
    4.  Tarkastele yhteenvetoa.
    5.  *(Vaihtoehto)* Kokeile raahata `app`-kansio Explorerista chatin syötekenttään.

### Harjoitus 1.6: Symbolin tutkiminen (`#` symboliviittaus, `/explain`)

* **Tarkoitus:** Ymmärtää tietty funktio tai luokka.
* **Tavoite:** Harjoittele symboliviittausta `#`-etuliitteellä ja interaktiivisella valinnalla.
* **Vaiheet:**
    1.  Avaa Copilot Chat -näkymä.
    2.  **Esimerkki 1 (Metodi):**
        * Kirjoita `#` ja ala kirjoittaa `WorkspaceWeatherData`. Valitse *symboli* `OpenWeatherMapClient#fetchWeatherData`.
        * Lisää `/explain Selitä, mitä tämä metodi tekee, sen parametrit ja palautusarvo.` ja paina Enter.
    3.  **Esimerkki 2 (Luokka):**
        * Kirjoita `#` ja ala kirjoittaa `WeatherData`, valitse *luokkasymboli* `WeatherData`.
        * Lisää `/explain Selitä tämän luokan tarkoitus ja sen kentät.` ja paina Enter.
    4.  Analysoi selitykset.

### Harjoitus 1.7: Ulkoisen tiedon hakeminen (`#fetch`, `/explain`)

* **Tarkoitus:** Hakea tietoa ulkoisesta URL-osoitteesta.
* **Tavoite:** Harjoittele `#fetch`-muuttujan käyttöä.
* **Vaiheet:**
    1.  README mainitsee OpenWeatherMap API:n. Kysy sen nykyisen säädatan päätepisteestä.
    2.  Avaa Copilot Chat -näkymä.
    3.  Kirjoita:
        ```
        #fetch:[https://openweathermap.org/current](https://openweathermap.org/current) /explain Mitkä ovat tärkeimmät parametrit tämän API:n kutsuun ja mitä tietoja JSON-vastaus yleensä sisältää (esim. lämpötila, sääkuvaus)?
        ```
    4.  Tarkastele Copilotin yhteenvetoa.

### Harjoitus 1.7: API-dokumentaation ja `WeatherData.java`:n vastaavuuksien tarkastelu (`#fetch`, `#file`, `/explain`)

* **Tarkoitus:** Ymmärtää, miten `WeatherData.java`-luokan kentät vastaavat OpenWeatherMap API:n tarjoamaa todellista dataa.
* **Tavoite:** Harjoitella `#fetch`-komennon käyttöä API-skeematietojen noutamiseen ja `#file`-komennon käyttöä projektitiedoston referoimiseen vertailua varten.
* **Vaiheet:**
    1.  Avaa Copilot Chat -näkymä.
    2.  Kirjoita seuraava kehotus:
        ```
        /explain Perustuen API-dokumentaatioon osoitteesta #fetch:https://openweathermap.org/current ja tiedoston #file:src/main/java/com/weather/app/WeatherData.java kenttiin, mitkä `WeatherData`-luokkamme kentät vastaavat suoraan API-vastauksen kenttiä? Onko `WeatherData`-luokassa kenttiä, jotka ovat johdettuja tai eivät suoraan löydy API-vastauksen pääosasta (esim. `main`, `wind`, `weather[]` sisältä)?
        ```
    3.  Tarkastele Copilotin analyysiä. Tämä auttaa ymmärtämään sovelluksesi ydintietolähteen.

### Harjoitus 1.8: VS Code -kysymykset (`@vscode`, `/explain`)

* **Tarkoitus:** Saada apua VS Code -ominaisuuksista tai asetuksista.
* **Tavoite:** Harjoittele `@vscode`-osallistujan käyttöä editoriympäristön kysymyksiin. Muista vain yksi osallistuja per pyyntö.
* **Vaiheet:**
    1.  Avaa Copilot Chat -näkymä.
    2.  Keksi Java-kehitykseen liittyvä kysymys (esim. alla).
    3.  Kirjoita pyyntö käyttäen `@vscode`:
        * Esim. 1: `@vscode /explain Miten voin nähdä Java-metodin määrittelyn nopeasti poistumatta nykyisestä tiedostosta?`
        * Esim. 2: `@vscode /explain Voiko Java-tiedostojen importit järjestää automaattisesti tallennettaessa?`
        * Esim. 3: `@vscode /explain Miten määrittelen tehtäväkonfiguraation VS Codessa tiettyjen Maven-komentojen suorittamiseen?`
    4.  Tarkastele selitystä.

### Harjoitus 1.9: Terminaalikomentojen ymmärtäminen (`#terminalLastCommand`, `/explain`)

* **Tarkoitus:** Selittää terminaalissa suoritettuja komentoja.
* **Tavoite:** Harjoittele `#terminalLastCommand`-muuttujan käyttöä.
* **Vaiheet:**
    1.  Avaa integroitu terminaali (Näytä > Terminaali).
    2.  Suorita projektiin liittyvä komento, esim.:
        ```bash
        mvn clean package -DskipTests
        ```
    3.  Odota, että komento valmistuu.
    4.  Avaa Copilot Chat -näkymä.
    5.  Kirjoita:
        ```
        #terminalLastCommand /explain Selitä viimeksi suoritettu komento ja käytettyjen lippujen (kuten -DskipTests) tarkoitus.
        ```
    6.  Tarkastele selitystä.

### Harjoitus 1.10: Terminaalin tulosteen selittäminen (`#terminalSelection`, `/explain`)

* **Tarkoitus:** Selittää tietty osa terminaalin tulosteesta.
* **Tavoite:** Harjoittele `#terminalSelection`-muuttujan käyttöä.
* **Vaiheet:**
    1.  Suorita terminaalissa komento, joka tuottaa tulostetta, esim.:
        ```bash
        mvn --version
        ```
    2.  **Valitse tietty osa** tulosteesta, esim. rivi, jossa näkyy Java-versio tai Mavenin kotihakemisto.
    3.  Avaa Copilot Chat -näkymä.
    4.  Kirjoita:
        ```
        #terminalSelection /explain Mitä valittu rivi terminaalin tulosteessa tarkoittaa kehitysympäristön kannalta?
        ```
    5.  Tarkastele selitystä.

### Harjoitus 1.11: Symbolin käyttökohteiden etsiminen (`#usage`)

* **Tarkoitus:** Ymmärtää, missä tiettyä luokkaa, metodia tai muuttujaa käytetään projektissa.
* **Tavoite:** Harjoittele `#usage`-muuttujan käyttöä ja interaktiivista symbolin valintaa.
* **Vaiheet:**
    1.  Avaa Copilot Chat -näkymä.
    2.  Mieti symboli, jonka käyttökohteet haluat löytää (esim. `WeatherData`-luokka tai `WeatherService#getWeather`-metodi).
    3.  Kirjoita `#` ja ala kirjoittaa symbolin nimeä. Valitse haluttu *symboli* (esim. `#usage:WeatherData`).
    4.  Lisää `/explain Missä tätä symbolia käytetään koodipohjassa? Listaa tiedostot ja rivit.` ja paina Enter.
    5.  Tarkastele Copilotin löytämät kohdat.

### Harjoitus 1.12: Rajapinnan toteutusten etsiminen (`#usage`)

* **Tarkoitus:** Selvittää kaikki luokat, jotka toteuttavat tietyn rajapinnan.
* **Tavoite:** Harjoittele `#usage`-muuttujan käyttöä rajapintasymbolille.
* **Vaiheet:**
    1.  Projektissa on `WeatherApiClient`-rajapinta. Etsi sen toteutukset.
    2.  Avaa Copilot Chat -näkymä.
    3.  Kirjoita `#` ja ala kirjoittaa `WeatherApiClient`. Valitse *rajapintasymboli*.
    4.  Lisää `/explain Etsi kaikki luokat, jotka toteuttavat tämän rajapinnan.` ja paina Enter.
    5.  Copilotin pitäisi tunnistaa esim. `OpenWeatherMapClient` toteutukseksi.

---

## Osa 2: Uusien ominaisuuksien ideointi Copilot Chatilla

**Tavoite:** Käytä Copilot Chatia ideointikumppanina hyödyntäen koodipohjan ymmärrystä (`#codebase` tai `@workspace`).

---

### Harjoitus 2.1: Ominaisuusehdotusten ideointi (`#codebase`)

* **Tarkoitus:** Generoida lista mahdollisista parannuksista.
* **Tavoite:** Harjoittele `#codebase`-muuttujan käyttöä luoviin ehdotuksiin.
* **Vaiheet:**
    1.  Avaa Copilot Chat -näkymä.
    2.  Kirjoita:
        ```
        #codebase Ehdota 3-5 uutta ominaisuutta tai merkittävää parannusta tälle komentorivisääsovellukselle. Selitä lyhyesti kunkin hyöty.
        ```
    3.  Tarkastele ehdotuksia.

### Harjoitus 2.2: Idean tarkempi tarkastelu (`#codebase`)

* **Tarkoitus:** Syventää yhden ominaisuuden toteutusta.
* **Tavoite:** Harjoittele keskustelua `#codebase`-kontekstissa.
* **Vaiheet:**
    1.  Valitse yksi idea (esim. 3 päivän ennuste).
    2.  Kysy Copilot Chatissa:
        ```
        #codebase Tarkastellaan 3 päivän ennusteen lisäämistä. Miten sovellusta pitäisi muuttaa? Tarvitaanko uusia API-kutsuja (tarkista OpenWeatherMap-dokumentaatio tarvittaessa)? Miten tulostus muuttuisi? Mitä luokkia pitäisi muokata?
        ```
    3.  Keskustele lähestymistavasta Copilotin kanssa.

### Harjoitus 2.3: Virheenkäsittelyn parantaminen (`#codebase`)

* **Tarkoitus:** Tunnistaa kohtia, joissa virheenkäsittelyä voisi parantaa.
* **Tavoite:** Harjoittele `#codebase`-muuttujan käyttöä analyysiin.
* **Vaiheet:**
    1.  Kirjoita Copilot Chatissa:
        ```
        #codebase Arvioi sovelluksen virheenkäsittelyä (esim. WeatherService, OpenWeatherMapClient, WeatherApp main-metodi). Ehdota tapoja tehdä siitä kestävämpää tai antaa parempaa palautetta käyttäjälle virhetilanteissa kuten virheellinen API-avain, verkkoyhteysongelmat, kaupunkia ei löydy tai odottamattomat API-vastaukset.
        ```
    2.  Arvioi ehdotukset.

### Harjoitus 2.4: Ominaisuuden toteutettavuuden arviointi API-dokumentaation avulla (`#codebase`, `#fetch`, `/explain`)

* **Tarkoitus:** Selvittää, tukeeko OpenWeatherMap API mahdollista uutta ominaisuutta (esim. "tuntuu kuin" -lämpötilan näyttäminen) ja miten siihen voisi päästä käsiksi.
* **Tavoite:** Harjoitella `#fetch`-komennon käyttöä API-dokumentaation konsultointiin uusia ominaisuuksia harkittaessa ja `#codebase`-komennon käyttöä nykyisen sovelluksen kontekstin tarjoamiseen.
* **Vaiheet:**
    1.  Avaa Copilot Chat -näkymä.
    2.  Oleta, että harkitset "tuntuu kuin" -lämpötilalukeman lisäämistä sääennusteeseen.
    3.  Kirjoita seuraava kehotus:
        ```
        #codebase Haluan lisätä "tuntuu kuin" -lämpötilan säänäyttöömme. Onko "tuntuu kuin" -lämpötilatietoa saatavilla API-dokumentaation #fetch:https://openweathermap.org/current mukaan? Jos on, mikä API-vastauksen kenttä tarjoaa tämän tiedon, ja miten voisin lisätä sen `WeatherData`-luokkaan ja sovelluksen tulosteeseen?
        ```
    4.  Tarkastele Copilotin vastausta ymmärtääksesi, onko ominaisuus toteutettavissa nykyisellä API:lla, ja saadaksesi alustavia ohjeita toteutukseen.

---

## Osa 3: Ominaisuuksien toteutus Copilotilla

**Tavoite:** Käytä Copilotin koodigenerointiominaisuuksia (täydennys, Edits-tila, agentit, slash-komennot, inline chat) toteuttaaksesi muutoksia. Käytä `#codebase`-muuttujaa laajaa kontekstia vaativissa tehtävissä.

---

### Harjoitus 3.1: Uuden kentän lisääminen (Täydennys & Edits-tila)

* **Tarkoitus:** Lisää tietoa, käytä Edits-tilaa.
* **Tavoite:** Harjoittele täydennystä & Edits-tilaa.
* **Vaiheet:**
    1.  **Muokkaa `WeatherData.java` (Täydennys):**
        * Lisää `private int humidity;`. Käytä täydennystä getter/setterille (`getH`, `setH`). Lisää `int humidity` konstruktorin parametreihin ja `this.humidity = humidity;`.
    2.  **Muokkaa `OpenWeatherMapClient.java` (Edits-tila):**
        * Etsi kohta, jossa `WeatherData`-olio luodaan JSON-parsinnan jälkeen.
        * **Valitse koodirivit**, joissa arvot poimitaan ja `WeatherData` luodaan.
        * Avaa Copilot Chat.
        * **Valitse "Edits"-tila** chatin valikosta.
        * Kirjoita ohje:
            ```
            Poimi 'humidity'-arvo JSON:n 'main'-osiosta ja välitä se WeatherData-konstruktorille muiden arvojen mukana.
            ```
        * Paina Enter. Hyväksy ehdotettu muutos.
    3.  **Muokkaa `WeatherApp.java` (Tulostus - Täydennys):**
        * Etsi `System.out.println`-rivi. Lisää siihen humidity, esim. aloita kirjoittamalla `+ ", Humidity: " + weatherData.getH`.

### Harjoitus 3.2: Yksikkötestien generointi (`#` tiedostoviittaukset, `/tests`)

* **Tarkoitus:** Generoida yksikkötestejä automaattisesti.
* **Tavoite:** Harjoittele `/tests`-komentoa tiedostoviittauksilla.
* **Vaiheet:**
    1.  Avaa Copilot Chat.
    2.  Kirjoita `#` ja valitse `src/test/java/com/weather/app/OpenWeatherMapClientTest.java`.
    3.  Kirjoita `#` ja valitse `src/main/java/com/weather/app/OpenWeatherMapClient.java`.
    4.  Lisää:
        ```
        /tests Generoi uusi JUnit-testimetodi OpenWeatherMapClientille. Mockkaa HTTP-kutsu ja JSON-vastaus (sis. 'main: { humidity: 85 }') ja varmista, että fetchWeatherData parsii humidityn oikein WeatherData-olioon.
        ```
    5.  Kopioi testimetodi testitiedostoon, säädä importit/mokit ja aja testit (`mvn test`).

### Harjoitus 3.3: Refaktorointi Edits-tilassa

* **Tarkoitus:** Muokkaa olemassa olevaa koodia Edits-tilassa.
* **Tavoite:** Harjoittele Edits-tilaa refaktorointiin.
* **Vaiheet:**
    1.  *(Oletus: `ConfigUtil.java` sisältää `getApiKey()`, joka lukee vain ympäristömuuttujan.)*
    2.  Avaa `src/main/java/com/weather/app/ConfigUtil.java`.
    3.  **Valitse koko `getApiKey()`-metodin runko.**
    4.  Avaa Copilot Chat.
    5.  **Valitse "Edits"-tila**.
    6.  Kirjoita ohje:
        ```
        Refaktoroi: Kokeile ensin 'OPENWEATHERMAP_API_KEY' ympäristömuuttujaa. Jos se on null tai tyhjä, yritä ladata 'config.properties' classpathista ja lue 'openweathermap.api.key'. Jos ei löydy, heitä IllegalStateException.
        ```
    7.  Hyväksy ehdotettu muutos.

### Harjoitus 3.4: Uuden komponentin luominen (`#codebase`, `/new`)

* **Tarkoitus:** Käytä Copilot Agentia (`/new`) rungon luomiseen.
* **Tavoite:** Harjoittele `/new`-komentoa `#codebase`-kontekstissa.
* **Vaiheet:**
    1.  Avaa Copilot Chat.
    2.  Kirjoita:
        ```
        #codebase /new Luo uusi Java-luokka 'WeatherCache' pakettiin 'com.weather.app.cache'. Sisällytä:
        1. Yksityinen staattinen final ConcurrentHashMap<String, CacheEntry> cache.
        2. Yksityinen staattinen final long CACHE_DURATION_MS = 300000; // 5 min
        3. Sisäinen staattinen luokka 'CacheEntry', jossa 'WeatherData data' ja 'long timestamp'.
        4. Julkinen staattinen void put(String city, WeatherData data).
        5. Julkinen staattinen Optional<WeatherData> get(String city), joka tarkistaa vanhentumisen.
        Lisää Javadoc.
        ```
    3.  Copilot ehdottaa uuden tiedoston (`src/main/java/com/weather/app/cache/WeatherCache.java`) ja paketin luomista. Hyväksy.
    4.  *(Jatko)* Integroi `WeatherCache` `WeatherService`-luokkaan (voit käyttää Edits-tilaa).

### Harjoitus 3.5: Koodimuutosten tarkastelu (`#changes`, `/explain`)

* **Tarkoitus:** Yhteenveto odottavista muutoksista.
* **Tavoite:** Harjoittele `#changes`-muuttujan käyttöä.
* **Vaiheet:**
    1.  Tee pieniä muutoksia yhteen tai kahteen tiedostoon (esim. lisää kommentti `WeatherApp.java`:aan, muuta tulostusmuotoa).
    2.  **Tallenna tiedostot.**
    3.  Avaa Lähdekoodin hallinta (yleensä Git-ikoni). Näet muutetut tiedostot "Changes"-listassa.
    4.  *(Valinnainen)* Stagea yksi muutos, jätä toinen stagettomaksi.
    5.  Avaa Copilot Chat.
    6.  Kirjoita:
        ```
        #changes /explain Yhteenveto nykyisten stagettujen ja stagettomien muutosten pääteemoista.
        ```
    7.  Tarkastele yhteenvetoa.

### Harjoitus 3.6: Copilotin mukauttaminen jaetulla ohjeella

* **Tarkoitus:** Vaikuta Copilotin generointiin `.github/copilot-instructions.md`-tiedostolla.
* **Tavoite:** Määrittele ohje, tarkkaile vaikutusta.
* **Vaiheet:**
    1.  **Luo ohjetiedosto:**
        * Luo projektin juureen kansio `.github`, jos sitä ei ole.
        * Luo `.github`-kansioon tiedosto `copilot-instructions.md`.
    2.  **Määrittele ohje:**
        * Lisää tiedostoon:
            ```markdown
            # Copilot-ohjeet Simple Weather CLI -sovellukselle

            ## Java-kehitysohjeet

            - **Lokitus:** Sovelluksen lokitukseen käytä aina `java.util.logging.Logger`-luokkaa. Hanki logger-olio `Logger.getLogger(LuokanNimi.class.getName())`. Älä käytä `System.out.println` tai `System.err.println` rutiinilokitukseen. Lokita poikkeukset catch-lohkossa loggerin `log`-metodilla tasolla `SEVERE` ja välitä poikkeus.
            ```
        * Tallenna tiedosto. *Huom: Copilot huomioi ohjeen automaattisesti hetken kuluttua.*
    3.  **Sovella ohjetta (Lisää lokitus):**
        * Avaa `src/main/java/com/weather/app/WeatherService.java`.
        * Etsi `getWeather(String city)`-metodi.
        * **Valitse koko metodin runko.**
        * Avaa Copilot Chat.
        * **Valitse "Edits"-tila**.
        * Kirjoita ohje:
            ```
            Lisää lokitus tähän metodiin:
            1. Alussa loggaa pyydetty kaupunki INFO-tasolla.
            2. Onnistuneen haun jälkeen loggaa lämpötila INFO-tasolla.
            3. Catch-lohkossa loggaa WeatherApiException SEVERE-tasolla ja välitä poikkeus.
            Luo logger-olio, jos sitä ei ole.
            ```
        * Paina Enter.
    4.  **Tarkista tulos:**
        * Tarkista, lisäsikö Copilot `java.util.logging.Logger`-olion ja käyttikö se `logger.log(Level.INFO, ...)` ja `logger.log(Level.SEVERE, ..., exception)`-kutsuja.
        * Jos ohje huomioitiin, hyväksy muutos.

### Harjoitus 3.7: Täysi toteutusprosessi (Ideointi -> Speksi -> Toteutus -> Refaktorointi)

* **Tarkoitus:** Simuloi pienen ominaisuuden kehityskulkua Copilotin avulla.
* **Tavoite:** Harjoittele Ask-tilaa ideointiin/speksiin, tiedostoviittauksia toteutuksen ohjaukseen ja Edits-tilaa viimeistelyyn.
* **Vaiheet:**
    1.  **A. Ideointi (Ask):** Kysy Copilot Chatissa:
        ```
        @workspace Ehdota yksinkertainen uusi ominaisuus tälle sääsovellukselle. Esim. liittyen yksiköihin tai tulostusmuotoon.
        ```
        Oletetaan, että Copilot ehdottaa lämpötilayksikön valintaa (Celsius/Fahrenheit).
    2.  **B. Speksaus (Ask):** Jatka keskustelua:
        ```
        Laadi lyhyt tekninen speksi Markdown-muodossa komentorivivalitsimelle (`--units`), jolla voi valita lämpötilayksikön (C tai F). Oletus on Celsius. Määrittele tarvittavat muutokset syötteen käsittelyyn, tietomalliin, palvelulogiikkaan ja tulostukseen.
        ```
    3.  **C. Tallenna speksi:** Kopioi speksi. Luo uusi tiedosto `docs/specs/UnitsFeature.md` (luo kansio tarvittaessa) ja liitä sisältö. Tallenna tiedosto.
    4.  **D. Toteutuksen suunnittelu (Ask):** Kysy Copilot Chatissa:
        ```
        #codebase # Explain Perustaen speksiin tiedostossa # (valitse UnitsFeature.md), listaa toteutusvaiheet. Mitä Java-tiedostoja pitää muuttaa ja mitä keskeisiä muutoksia kuhunkin (esim. argumenttien käsittely, tietomalli, palvelulogiikka, tulostus)?
        ```
        Tarkastele suunnitelmaa.
    5.  **E. Toteuta muutokset (Edits/Ask/Täydennys):** Toteuta suunnitelman mukaan:
        * Avaa argumenttien käsittelystä vastaava tiedosto (esim. `WeatherApp.java`). Käytä Edits-tilaa (valitse relevantti osio) tai Ask-tilaa ja pyydä ohjeita.
        * Avaa `WeatherData.java`. Jos speksi vaatii yksikön tallennusta tai muunnosta, käytä Edits-tilaa tai kysy ohjeita.
        * Avaa `WeatherApp.java` (tai muu tulostusluokka). Käytä Edits-tilaa tai kysy ohjeita tulostuksen päivittämisestä.
    6.  **F. Viimeistele (Edits):** Tarkista toteutettu koodi. Valitse parannettavat kohdat ja käytä Edits-tilaa esim. "Refaktoroi lämpötilamuunnos selkeämmäksi" tai "Lisää virheenkäsittely, jos --units on virheellinen".

### Harjoitus 3.8: Inline Chat -ehdotusten selaaminen

* **Tarkoitus:** Harjoittele useiden Copilotin inline chat -ehdotusten selaamista.
* **Tavoite:** Käytä inline chatia yksinkertaiseen tehtävään ja selaa eri vaihtoehtoja.
* **Vaiheet:**
    1.  Avaa `src/main/java/com/weather/app/WeatherData.java`.
    2.  Valitse koko konstruktorin määrittely.
    3.  Avaa inline chat (Oletus: `Cmd+I` / `Ctrl+I`).
    4.  Kirjoita: `/doc Generoi Javadoc tälle konstruktorille.` ja paina Enter.
    5.  Copilot näyttää ensimmäisen ehdotuksen.
    6.  **Selaa ehdotuksia:** Käytä pikanäppäintä seuraavaan/edelliseen ehdotukseen (esim. `Alt+]` / `Alt+[` tai `Option+]` / `Option+[`).
    7.  Valitse haluamasi ehdotus ja hyväksy se.

---

## Osa 4: Valinnaiset jatkoharjoitukset

**Tavoite:** Tutustu Copilotin edistyneempiin käyttötapoihin perusprosessien lisäksi.

---

### Harjoitus 4.1: Debuggausapu (ajonaikaiset virheet)

* **Tarkoitus:** Harjoittele Copilot Chatia ajonaikaisten virheiden ymmärtämiseen.
* **Tavoite:** Käytä tiedostoviittausta ja liitä stack trace kysymykseen.
* **Vaiheet:**
    1.  *(Valinnainen: aiheuta virhe)* Muokkaa `OpenWeatherMapClient.java`:ta. Poista väliaikaisesti null-tarkistukset kentästä (esim. `humidity`) tai muuta testin mock-vastausta.
    2.  **Aiheuta virhe:** Aja sovellus (`java -jar ...`) tai yksikkötesti (`mvn test`), jotta virhe laukeaa (esim. NullPointerException).
    3.  **Kopioi stack trace:** Valitse ja kopioi koko stack trace terminaalista.
    4.  **Kysy Copilotilta:** Avaa Copilot Chat. Kirjoita pyyntö tiedostoviittauksella ja liitä stack trace:
        ```
        # (valitse OpenWeatherMapClient.java) /explain Sain seuraavan ajonaikaisen virheen. Koodin ja stack tracin perusteella, mikä voisi olla syy? Mitä tarkistuksia tai korjauksia suosittelet?

        [Liitä stack trace tähän]
        ```
    5.  **Analysoi ehdotus:** Tarkastele Copilotin selitystä ja korjausehdotuksia.

### Harjoitus 4.2: Commit-viestin generointi

* **Tarkoitus:** Hyödynnä Copilotia Git commit -viestin luonnissa.
* **Tavoite:** Käytä `#changes`-kontekstia pyytääksesi Copilotia laatimaan commit-viestin.
* **Vaiheet:**
    1.  **Varmista muutokset:** Tee muutoksia, joita ei ole vielä commitoitu.
    2.  **Avaa Copilot Chat.**
    3.  **Generoi commit-viesti:** Kirjoita:
        ```
        #changes /explain Laadi ytimekäs Git commit -viesti näistä muutoksista. Noudata Conventional Commits -formaattia (esim. 'feat:', 'fix:', 'refactor:', 'test:', 'docs:', 'chore:').
        ```
    4.  **Tarkista:** Onko viesti kuvaava ja oikeassa muodossa? Voit käyttää sitä pohjana oikeaan commitiin.

### Harjoitus 4.3: Koodikatselmointi (turvallisuus & suorituskyky) (`#codebase`)

* **Tarkoitus:** Käytä Copilotia alustavana katselmoijana mahdollisten ongelmakohtien tunnistamiseen.
* **Tavoite:** Harjoittele kysymysten esittämistä turvallisuudesta ja suorituskyvystä `#codebase`-kontekstissa.
* **Vaiheet:**
    1.  **Avaa Copilot Chat.**
    2.  **Kysy turvallisuudesta:** Kirjoita:
        ```
        #codebase /explain Arvioi sovelluksen turvallisuutta, erityisesti miten OpenWeatherMap API -avain käsitellään ja miten ulkoista dataa haetaan ja käsitellään. Onko mahdollisia haavoittuvuuksia tai puutteita?
        ```
        Tarkastele analyysiä.
    3.  **Kysy suorituskyvystä:** Kirjoita:
        ```
        #codebase /explain Analysoi sovelluksen koodia, erityisesti API-kutsut, JSON-parsinta ja datan käsittely. Onko ilmeisiä pullonkauloja tai optimointiehdotuksia?
        ```
        Tarkastele ehdotuksia.

### Harjoitus 4.4: Vaihtoehtoisten toteutusten tutkiminen

* **Tarkoitus:** Pyydä Copilotilta vaihtoehtoisia tapoja toteuttaa sama ohjelmointitehtävä.
* **Tavoite:** Käytä `#selection`- ja tiedostoviittausta pyytääksesi vaihtoehtoja.
* **Vaiheet:**
    1.  **Valitse koodi:** Avaa `src/main/java/com/weather/app/OpenWeatherMapClient.java`. Valitse JSON-parsinnasta WeatherData-olion luontiin liittyvä koodi.
    2.  **Avaa Copilot Chat.**
    3.  **Lisää konteksti ja pyyntö:**
        * Kirjoita `#selection`.
        * Kirjoita `#` ja valitse `pom.xml`.
        * Lisää:
          ```
          /explain Näytä vaihtoehtoinen tapa toteuttaa valitun koodin toiminnallisuus (JSON -> Java-olio). Voiko sen tehdä Jacksonin toisella ominaisuudella tai jollain muulla kirjastolla (tarkista pom.xml)? Pohdi lyhyesti etuja/haittoja.
          ```
    4.  **Arvioi vaihtoehdot:** Tarkastele Copilotin ehdotuksia ja vertaile niitä alkuperäiseen.

---

### Huomio edistyneestä mukautuksesta: Uudelleenkäytettävät prompt-tiedostot

`.github/copilot-instructions.md`-tiedoston lisäksi Copilot tukee **uudelleenkäytettäviä prompt-tiedostoja** (yleensä `.prompt.md`-päätteellä, ominaisuus voi olla kokeellinen).

Näillä voi määritellä monimutkaisempia, monivaiheisia ohjeita toistuvia tehtäviä varten (esim. vakiorefaktorointi, koodigenerointi mallista, tarkistuslista katselmointiin). Voit käyttää paikkamerkkejä ja yhdistää ohjeita kontekstimuuttujiin. Vaikka tässä ei ole erillistä harjoitusta tästä, ominaisuus kannattaa tutkia, jos annat Copilotille usein samoja monimutkaisia ohjeita. Katso ajantasaiset ohjeet VS Coden Copilot-dokumentaatiosta.
