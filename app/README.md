# Aplicație pentru căutarea raselor de câini #
Autor: Rizea Adelina-Maria

## Descriere
Scopul aplicației este să permită utilizatorilor să caute diferite rase de câini și informații detaliate despre ele, văzând în același timp și imagini cu acestea. De asemenea, utilizatorii vor putea adăuga diferite rase la favorite și vor putea lăsa review-uri/note, iar în funcție de acestea vor primi recomandări pentru alte rase de câini care li s-ar putea potrivi. Utilizatorul va avea posibilitatea descarcarii imaginilor preferate online. Pentru realizarea aplicației se va folosi thedogapi (https://api.thedogapi.com/). 

## Ecrane și funcționalități ##
#### Ecranul principal ####
- Va afișa o imagine la întâmplare cu o rasă de câine.
- Imaginea, odată apăsată, va duce la o pagină cu detalii despre rasa respectivă.
- Va exista un TextField pentru căutarea unei anumite rase.
- Dacă utilizatorul are rase salvate la favorite, pe acest ecran vor apărea și recomandări pentru alte rase de câini.

#### Funcționalitatea de căutare ####
- Utilizatorul poate introduce numele unei rase în câmpul de căutare.
- La selectarea unei rase, aplicația va deschide un nou ecran cu detalii specifice și o imagine reprezentativă.
- Pe acest ecran, utilizatorul poate:
  - adăuga rasa la favorite;
  - adăuga un review;

#### Ecranul pentru favorite ####
- Va exista un al treilea ecran dedicat vizualizării raselor salvate la favorite.
- Se vor afișa și comentariile adăugate pentru fiecare rasă favorită.

#### Alte funcționalități ####
- Aplicația va putea fi utilizată atât în modul întunecat, cât și în modul luminos.
- Rasele adăugate la favorite vor fi salvate în baza de date locală, utilizând Room.

## Tehnologii si API ##
### Tehnologii ###
În cadrul aplicației voi folosi: Kotlin, Jetpack Compose, Retrofit, Room, Jetpack Navigation.

### Endpoint-urile oferite de API-ul ales și folosite în cadrul aplicației sunt: ###
- https://api.thedogapi.com/v1/images/search?breed_id={id}: informatii despre o anumita rasa
- https://api.thedogapi.com/v1/images/search/: imagine cu o rasa oarecare
- https://api.thedogapi.com/v1/breeds/: informații despre toate rasele (folosit pentru a extrage id-ul unei rase pentru obținerea pozei și pentru a extrage informațiile necesare)

## Stadiul actual ##
Până în momentul actual am realizat funcționalitățile pentru afișarea unei imagini cu o rasă aleatoare și căutarea unei anumite rase împreună cu afișarea detaliilor specifice. Detaliile oferite de API sunt inaltime, greutate, tipul rasei si grupul din care face parte.
  


