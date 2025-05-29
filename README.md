# Aplicație pentru căutarea rasei de câini potrivite #
Autor: Rizea Adelina-Maria

## Descriere
Scopul aplicației este să permită utilizatorilor să caute diferite rase de câini și informații detaliate despre ele, văzând în același timp și imagini cu acestea. În funcție de temperamentul rasei căutate, ei vor primi recomandări și vor putea vizualiza pe rând paginile raselor recomandate. De asemenea, utilizatorii vor putea adăuga diferite rase la favorite pentru a le vizualiza mai târziu, vor putea lăsa review-uri și vor putea descărca imaginea unei rase dorite. Pentru realizarea aplicației se va folosi thedogapi (https://api.thedogapi.com/). 

## Ecrane și funcționalități ##
#### Ecranul principal ####
- Va afișa o imagine la întâmplare cu o rasă de câine.
- Va exista un TextField pentru căutarea unei anumite rase.
- Vor fi prezente butoanele pentru vizualizarea listei de favorite și logout.

#### Funcționalitatea de căutare ####
- După logare, utilizatorul poate introduce numele unei rase în câmpul de căutare.
- La selectarea unei rase, aplicația va deschide un nou ecran cu detalii specifice și o imagine reprezentativă.
- Pe acest ecran, utilizatorul poate:
  - adăuga rasa la favorite;
  - adăuga un review;
  - descărca imaginea de pe ecran;
  - vizualiza recomandările celorlalte rase;
  - naviga către paginile raselor recomandate.

#### Ecranul pentru favorite ####
- Va exista un al treilea ecran dedicat vizualizării raselor salvate la favorite.
- Vor fi afișate review-urile adăugate de utilizator pentru fiecare rasă favorită.
- Rasele adăugate la favorite vor putea fi șterse din listă.

#### Alte funcționalități ####
- Userii și rasele adăugate la favorite vor fi salvate în baza de date locală, utilizând Room.

## Tehnologii și API ##
### Tehnologii ###
În cadrul aplicației voi folosi: Kotlin, Jetpack Compose, Retrofit, Room, Jetpack Navigation, Gson.

### Endpoint-urile oferite de API-ul ales și folosite în cadrul aplicației sunt: ###
- https://api.thedogapi.com/v1/images/search?breed_id={id}: informații despre o anumită rasă
- https://api.thedogapi.com/v1/images/search/: imagine cu o rasă oarecare
- https://api.thedogapi.com/v1/breeds/: informații despre toate rasele (folosit pentru a extrage id-ul unei rase pentru obținerea pozei și pentru a extrage informațiile necesare)

## Structura ##
### Structura proiectului respectă arhitectura recomandată: ###
- Datele (modele, DAO, repository, Retrofit) sunt grupate în pachete (data/api/ și data/model). De asemenea, există un repository pentru gestionarea accesului la date.
- Logica de business este gestionată în ViewModel-uri, fiecare ecran având propriul ViewModel și UiState care pot fi găsite în ui/screens.
- Detaliile pentru interfața cu utilizatorul se află în pachetul ui/screens, unde fiecare ecran are propriul fișier Composable.
- Navigarea este centralizată în MainActivity.
