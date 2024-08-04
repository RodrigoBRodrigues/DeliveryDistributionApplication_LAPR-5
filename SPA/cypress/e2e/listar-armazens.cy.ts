describe('Login -> Menu Gestor Armazém -> Listar Armazéns', () => {
    it('Login', () => {
      cy.visit('/')
      cy.url().should('includes','login');
      cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("armazem");
      cy.get('button').click();
    })
    it('Menu Gestor Armazém', () => {
        cy.url().should('include','menu-gestor-armazem');
        cy.get('#listar-armazens').click();
    })
    it('Listar Armazéns', () => {
        cy.url().should('include','listar-armazens');
    })
})


describe('Criar Armazéns válidos e verificar que ele está presente na Listagem', () => {
    it('Login', () => {
        cy.visit('/')
        cy.url().should('includes','login');
        cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("armazem");
        cy.get('button').click();
      })

      it('Menu Gestor Armazém', () => {
          cy.url().should('include','menu-gestor-armazem');
          cy.get('#criar-armazem').click();
      })

      it('Criar Armazém', () => {
          cy.url().should('include','criar-armazem');
      })

      it('Inserir dados válidos', () => {
        cy.viewport(1920, 1080);
        cy.get('[placeholder="Introduzir Identificador do Armazém"').type('A97');
        cy.get('[placeholder="Introduzir Designação"]').type("Castro");
        cy.get('[placeholder="Introduzir Rua"]').type("Modelo");
        cy.get('[placeholder="Introduzir Código Postal"]').type("3355-852");
        cy.get('[placeholder="Introduzir Latitude"]').type("25");
        cy.get('[placeholder="Introduzir Longitude"]').type("31");
        cy.get('[placeholder="Introduzir Altitude"]').type("5");
        cy.get('button').click();
        cy.on('window:alert',(t)=>{
            expect(t).to.contains('Armazém Criado!');
         })
         cy.url().should('include','criar-armazem');
    })

    it('Verificar que existe na listagem de Armazéns e criar outro Armazém', () => {
        cy.visit('/menu-gestor-armazem');
        cy.visit('/listar-armazens');
        cy.get('table').get('td').should('contains.text', 'A97');
        cy.get('table').get('td').should('contains.text', 'Castro');
        cy.get('table').get('td').should('contains.text', 'Modelo');
        cy.get('table').get('td').should('contains.text', '3355-852');
        cy.visit('/menu-gestor-armazem');
        cy.visit('/criar-armazem');
        cy.get('[placeholder="Introduzir Identificador do Armazém"').type('A98');
        cy.get('[placeholder="Introduzir Designação"]').type("Carreira");
        cy.get('[placeholder="Introduzir Rua"]').type("Continente");
        cy.get('[placeholder="Introduzir Código Postal"]').type("3351-852");
        cy.get('[placeholder="Introduzir Latitude"]').type("25");
        cy.get('[placeholder="Introduzir Longitude"]').type("35");
        cy.get('[placeholder="Introduzir Altitude"]').type("6");
        cy.get('button').click();
        cy.on('window:alert',(t)=>{
            expect(t).to.contains('Armazém Criado!');
         })
        cy.visit('/menu-gestor-armazem');
        cy.visit('/listar-armazens');
        cy.get('table').get('td').should('contains.text', 'A98');
        cy.get('table').get('td').should('contains.text', 'Carreira');
        cy.get('table').get('td').should('contains.text', 'Continente');
        cy.get('table').get('td').should('contains.text', '3351-852')
    })
})


describe('Filtro Id Listagem de Armazéns', () => {
        it('Login', () => {
            cy.visit('/')
            cy.url().should('includes','login');
            cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("armazem");
            cy.get('button').click();
          })
          it('Menu Gestor Armazém', () => {
              cy.url().should('include','menu-gestor-armazem');
              cy.get('#listar-armazens').click();
          })
          it('Listar Armazéns', () => {
              cy.url().should('include','listar-armazens');
          })
        
    
    
        it('Verificar que o Filtro Id funciona', () => {
            cy.get('table').get('[placeholder="Procure Armazéns por Id"]').type('A97');
            cy.get('table').get('td').should('contains.text', 'A97');
            cy.get('table').get('td').should('not.contains.text', 'A99');
            cy.get('table').get('td').should('not.contains.text', 'A98');
            cy.get('table').get('td').should('contains.text', 'Castro');
        })

    })


describe('Filtro Designacao Listagem de Armazéns', () => {
        it('Login', () => {
            cy.visit('/')
            cy.url().should('includes','login');
            cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("armazem");
            cy.get('button').click();
          })
          it('Menu Gestor Armazém', () => {
              cy.url().should('include','menu-gestor-armazem');
              cy.get('#listar-armazens').click();
          })
          it('Listar Armazéns', () => {
              cy.url().should('include','listar-armazens');
          })
        
        it('Verificar que o Filtro Id funciona', () => {
            cy.get('table').get('[placeholder="Procure Armazéns por Designação"]').type('Castro');
            cy.get('table').get('td').should('contains.text', 'Castro');
            cy.get('table').get('td').should('not.contains.text', 'Carvalhos');
            cy.get('table').get('td').should('not.contains.text', 'Carreira');
            cy.get('table').get('td').should('contains.text', 'A97');
        })
})

describe('Filtro Id e Designacao em conjunto Listagem de Armazéns', () => {
    it('Login', () => {
        cy.visit('/')
        cy.url().should('includes','login');
        cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("armazem");
        cy.get('button').click();
      })
      it('Menu Gestor Armazém', () => {
          cy.url().should('include','menu-gestor-armazem');
          cy.get('#listar-armazens').click();
      })
      it('Listar Armazéns', () => {
          cy.url().should('include','listar-armazens');
      })
    
    it('Verificar que o Filtro Id funciona', () => {
        cy.get('table').get('[placeholder="Procure Armazéns por Designação"]').type('Ca');
        cy.get('table').get('td').should('contains.text', 'Castro');
        cy.get('table').get('td').should('contains.text', 'Carvalhos');
        cy.get('table').get('td').should('contains.text', 'Carreira');
        cy.get('table').get('[placeholder="Procure Armazéns por Id"]').type('A97');
        cy.get('table').get('td').should('contains.text', 'Castro');
    })
})

describe('Desinibir Armazém', () => {
    it('Login', () => {
        cy.visit('/')
        cy.url().should('includes','login');
        cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("armazem");
        cy.get('button').click();
      })
      it('Menu Gestor Armazém', () => {
          cy.url().should('include','menu-gestor-armazem');
          cy.get('#listar-armazens').click();
      })
      it('Listar Armazéns', () => {
          cy.url().should('include','listar-armazens');
      })
      it('Desinibir Armazém', () => {
        cy.get('button').eq(20).click();
      })
 })
 describe('Verificar que consta na Listagem de Armazéns ao criar Entrega', () => {
    it('Login', () => {
        cy.visit('/')
        cy.url().should('includes','login');
        cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("armazem");
        cy.get('button').click();
      })
      it('Menu Gestor Armazém', () => {
          cy.url().should('include','menu-gestor-armazem');
          cy.get('#listar-armazens').click();
      })
      it('Listar Armazéns', () => {
          cy.url().should('include','listar-armazens');
      })
      it('Verificar que consta na Listagem de Armazéns ao criar Entrega', () => {
        cy.get('button').eq(20).click();
        cy.visit('/menu-gestor-armazem');
        cy.get('#criar-entrega').click();
        cy.get('select').should('contains.text', 'NNN');
      })
   })
   
   describe('Inibir Armazém', () => {
    it('Login', () => {
        cy.visit('/')
        cy.url().should('includes','login');
        cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("armazem");
        cy.get('button').click();
      })
      it('Menu Gestor Armazém', () => {
          cy.url().should('include','menu-gestor-armazem');
          cy.get('#listar-armazens').click();
      })
      it('Listar Armazéns', () => {
          cy.url().should('include','listar-armazens');
      })
      it('Inibir Armazém', () => {
        cy.get('button').eq(20).click();
      })
  })

    
  


