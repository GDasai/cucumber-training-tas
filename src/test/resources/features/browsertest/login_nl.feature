#language: nl

Functionaliteit: De gebruiker bezoekt de webpagina en logt in met valide inlog gegevens

  Scenario: De gebruiker logt in met valide inlog gegevens
    Gegeven de gebruiker heeft een browser geopend en bezoekt de login pagina
    Als de gebruiker inlogt met valide inlog gegevens
    En de gebruiker klikt op de aanmelden knop
    Dan komt de gebruiker op de homepagina van de cucumber website