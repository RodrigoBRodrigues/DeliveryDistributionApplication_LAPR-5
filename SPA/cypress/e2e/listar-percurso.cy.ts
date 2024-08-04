describe('Login -> Menu Gestor Percurso -> Listar Percursos', () => {
    it('Login', () => {
      cy.visit('/')
      cy.url().should('includes','login');
      cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("logistica");
      cy.get('button').click();
    })
    it('Menu Gestor Percurso', () => {
        cy.url().should('include','menu-gestor-logistica');
        cy.get('#listar-percursos').click();
    })
    it('Listar Percursos', () => {
        cy.url().should('include','listar-percursos');
    })
})


describe('Criar Percurso válidos e verificar que ele está presente na Listagem', () => {
    it('Login', () => {
        cy.visit('/')
        cy.url().should('includes','login');
        cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("logistica");
        cy.get('button').click();
      })

      it('Menu Gestor Percurso', () => {
          cy.url().should('include','menu-gestor-logistica');
          cy.get('#criar-percurso').click();
      })

      it('Criar Percurso', () => {
          cy.url().should('include','criar-percurso');
      })

      it('Inserir dados válidos', () => {
        cy.viewport(1920, 1080);
        cy.get('[placeholder="Introduzir Armazem de Partida"').type('Arouca');
        cy.get('[placeholder="Introduzir Armazem de Chegada"]').type('Espinho');
        cy.get('[placeholder="Introduzir distancia do percurso"]').type('20');
        cy.get('[placeholder="Introduzir tempo do percurso"]').type('20');
        cy.get('[placeholder="Introduzir Energia Gasta"]').type('20');
        cy.get('[placeholder="Introduzir tempo extra"]').type('20');
        cy.get('button').click();
        cy.on('window:alert',(t)=>{
            expect(t).to.contains('Percurso Criado');
         })
         cy.url().should('include','criar-percurso');
    })

    it('Verificar que existe na listagem de Percursos e criar outro Percurso', () => {
        cy.visit('/menu-gestor-logistica');
        cy.visit('/listar-percursos');
        cy.get('table').get('td').should('contains.text', 'Arouca');
        cy.get('table').get('td').should('contains.text', 'Espinho');
        cy.visit('/menu-gestor-logistica');
        cy.visit('/criar-percurso');

        cy.get('[placeholder="Introduzir Armazem de Partida"').type('Porto');
        cy.get('[placeholder="Introduzir Armazem de Chegada"]').type('Trofa');
        cy.get('[placeholder="Introduzir distancia do percurso"]').type('20');
        cy.get('[placeholder="Introduzir tempo do percurso"]').type('20');
        cy.get('[placeholder="Introduzir Energia Gasta"]').type('20');
        cy.get('[placeholder="Introduzir tempo extra"]').type('20');
        cy.get('button').click();
        cy.on('window:alert',(t)=>{
            expect(t).to.contains('Percurso Criado');
            })
         cy.url().should('include','criar-percurso');
    })
        cy.visit('/menu-gestor-logistica');
        cy.visit('/listar-percursos');
        cy.get('table').get('td').should('contains.text', 'Porto');
        cy.get('table').get('td').should('contains.text', 'Trofa');
    })


describe('Filtro Armazem Partida Listagem de Percursos', () => {
        it('Login', () => {
            cy.visit('/')
            cy.url().should('includes','login');
            cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("logistica");
            cy.get('button').click();
          })
          it('Menu Gestor Percurso', () => {
              cy.url().should('include','menu-gestor-logistica');
              cy.get('#listar-percursos').click();
          })
          it('Listar Percursos', () => {
              cy.url().should('include','listar-percursos');
          })
        
    
    
        it('Verificar que o Filtro Armazem Partida funciona', () => {
            cy.get('table').get('[placeholder="Armazem Partida"]').type('Arouca');
            cy.get('table').get('td').should('contains.text', 'Arouca');
            cy.get('table').get('td').should('not.contains.text', 'Porto');
        })

    })

describe('Filtro Armazem Chegada Listagem de Percursos', () => {
    it('Login', () => {
        cy.visit('/')
        cy.url().should('includes','login');
        cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("logistica");
        cy.get('button').click();
      })
      it('Menu Gestor Percurso', () => {
          cy.url().should('include','menu-gestor-logistica');
          cy.get('#listar-percursos').click();
      })
      it('Listar Percursos', () => {
          cy.url().should('include','listar-percursos');
      })
        
    
    
    it('Verificar que o Filtro Armazem Chegada funciona', () => {
        cy.get('table').get('[placeholder="Armazem Chegada"]').type('Espinho');
        cy.get('table').get('td').should('contains.text', 'Espinho');
        cy.get('table').get('td').should('not.contains.text', 'Trofa');
    })
})
    