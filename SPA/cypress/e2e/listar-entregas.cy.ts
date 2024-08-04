describe('Login -> Menu Gestor Armazém -> Listar Entregas', () => {
    it('Login', () => {
      cy.visit('/')
      cy.url().should('includes','login');
      cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("armazem");
      cy.get('button').click();
    })
    it('Menu Gestor Armazém', () => {
        cy.url().should('include','menu-gestor-armazem');
        cy.get('#listar-entregas').click();
    })
    it('Listar Entregas', () => {
        cy.url().should('include','listar-entregas');
    })
})


describe('Criar Entrega válidas e verificar que ele está presente na Listagem', () => {
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

  it('Criar Armazem', () => {
      cy.url().should('include','criar-armazem');
  })

  it('Inserir dados válidos', () => {
    cy.viewport(1920, 1080);
    cy.get('[placeholder="Introduzir Identificador do Armazém"').type('A99');
    cy.get('[placeholder="Introduzir Designação"]').type('Carvalhos');
    cy.get('[placeholder="Introduzir Rua"]').type('Loja Central');
    cy.get('[placeholder="Introduzir Código Postal"]').type('3350-852');
    cy.get('[placeholder="Introduzir Latitude"]').type('20');
    cy.get('[placeholder="Introduzir Longitude"]').type('30');
    cy.get('[placeholder="Introduzir Altitude"]').type('5');
    cy.get('button').click();
    cy.on('window:alert',(t)=>{
        expect(t).to.contains('Armazém Criado!');
     })
     cy.url().should('include','criar-armazem');
})

it('Verificar que existe na listagem de Armazéns', () => {
    cy.visit('/menu-gestor-armazem');
    cy.visit('/listar-armazens');
    cy.get('table').get('td').should('contains.text', 'A99');
    cy.get('table').get('td').should('contains.text', 'Carvalhos');
    cy.get('table').get('td').should('contains.text', 'Loja Central');
    cy.get('table').get('td').should('contains.text', '3350-852');
    cy.get('table').get('td').should('contains.text', '20');
    cy.get('table').get('td').should('contains.text', '30');
    cy.get('table').get('td').should('contains.text', '5');
})


it('Sucesso com todos os campos válidos', () => {
  cy.visit('/')
  cy.url().should('includes','login');
  cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("armazem");
  cy.get('button').click();
  cy.url().should('include','menu-gestor-armazem');
  cy.url().should('not.include','menu-gestor-frota');
  cy.url().should('not.include','menu-gestor-logistica');
  cy.get('button[id="criar-entrega"]').click()

  cy.get('select').select('A99');
  cy.get('[placeholder="Introduzir massa"]').clear().type('200');
  cy.get('[placeholder="Introduzir tempo colocar"]').clear().type('20');
  cy.get('[placeholder="Introduzir tempo retirar"]').clear().type('17');
  cy.get('[id="criar"]').click();
  cy.on('window:alert',(t)=>{
    //assertions
    expect(t).to.contains('Entrega Criada');
  })
})

    it('Verificar que existe na listagem de Entregas e criar outra Entrega', () => {
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
            expect(t).to.contains('Entrega Criada!');
         })
        cy.visit('/menu-gestor-armazem');
        cy.visit('/listar-armazens');
        cy.get('table').get('td').should('contains.text', 'A98');
        cy.get('table').get('td').should('contains.text', 'Carreira');
        cy.get('table').get('td').should('contains.text', 'Continente');
        cy.get('table').get('td').should('contains.text', '3351-852')
    })
})


describe('Filtro Id Listagem de Entregas', () => {
        it('Login', () => {
            cy.visit('/')
            cy.url().should('includes','login');
            cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("armazem");
            cy.get('button').click();
          })
          it('Menu Gestor Armazém', () => {
              cy.url().should('include','menu-gestor-armazem');
              cy.get('#listar-entregas').click();
          })
          it('Listar Armazéns', () => {
              cy.url().should('include','listar-entregas');
          })



        it('Verificar que o Filtro Id funciona', () => {
            cy.get('table').get('[placeholder="Procure Entrega por Armazem"]').type('A99');
            cy.get('table').get('td').should('contains.text', 'A99');
            cy.get('table').get('td').should('not.contains.text', 'A97');
            cy.get('table').get('td').should('not.contains.text', 'A98');
        })
    })


describe('Filtro Data Listagem de Entregas', () => {
        it('Login', () => {
            cy.visit('/')
            cy.url().should('includes','login');
            cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("armazem");
            cy.get('button').click();
          })
          it('Menu Gestor Armazém', () => {
              cy.url().should('include','menu-gestor-armazem');
              cy.get('#listar-entregas').click();
          })
          it('Listar Armazéns', () => {
              cy.url().should('include','listar-entregas');
          })

        it('Verificar que o Filtro data funciona', () => {
            cy.get('[placeholder="Procure Entregas com data de inicial"]').type("2022-01-01")
            cy.get('[placeholder="Procure Entregas com data de final"]').type("2025-05-15")
            cy.get('table').get('td').should('contains.text', 'A99');
            cy.get('table').get('td').should('not.contains.text', 'Carvalhos');
            cy.get('table').get('td').should('not.contains.text', 'Carreira');
        })
        it('Verificar que o Filtro data nao encontra encomendas fora das datas', () => {

          cy.get('[placeholder="Procure Entregas com data de inicial"]').type("2022-01-01")
          cy.get('[placeholder="Procure Entregas com data de final"]').type("2022-01-01")
          cy.get('table').get('td').should('not.exist');
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
          cy.get('#listar-entregas').click();
      })
      it('Listar Armazéns', () => {
          cy.url().should('include','listar-entregas');
      })

    it('Verificar que o Filtro Id funciona', () => {
        cy.get('table').get('[placeholder="Procure Entrega por Armazem"]').type('A99');
        cy.get('table').get('td').should('contains.text', 'A99');
    })
    it('Verificar que o Filtro data funciona', () => {
      const d = new Date()  // current date

      cy.get('[placeholder="Procure Entregas com data de inicial"]').type("2022-01-01")
      cy.get('[placeholder="Procure Entregas com data de final"]').type("2025-05-15")
      cy.get('table').get('td').should('contains.text', 'A99');
    })


})




