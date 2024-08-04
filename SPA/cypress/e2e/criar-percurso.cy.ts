describe('Login -> Menu Gestor Logística -> Criar Percurso', () => {
    it('Login', () => {
      cy.visit('/')
      cy.url().should('includes','login');
      cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("logistica");
      cy.get('button').click();
    })
    it('Menu Gestor Logística', () => {
        cy.url().should('include','menu-gestor-logistica');
        cy.get('#criar-percurso').click();
    })
    it('Criar Percurso', () => {
        cy.url().should('include','criar-percurso');
    })
})

describe('Criar Percurso válido e verificar que ele está presente na Listagem', () => {
    it('Login', () => {
        cy.visit('/')
        cy.url().should('includes','login');
        cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("logistica");
        cy.get('button').click();
      })

      it('Menu Gestor Logística', () => {
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

    it('Verificar que existe na listagem de Percursos', () => {
        cy.visit('/menu-gestor-logistica');
        cy.visit('/listar-percursos');
        cy.get('table').get('td').should('contains.text', 'Arouca');
        cy.get('table').get('td').should('contains.text', 'Espinho');
        cy.get('table').get('td').should('contains.text', '20');
        cy.get('table').get('td').should('contains.text', '20');
        cy.get('table').get('td').should('contains.text', '20');
        cy.get('table').get('td').should('contains.text', '20');
    })

})


describe('Criar Percurso com Tempo inválida e verificar que não está presente na Listagem', () => {
    it('Login', () => {
        cy.visit('/')
        cy.url().should('includes','login');
        cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("logistica");
        cy.get('button').click();
      })

      it('Menu Gestor Logística', () => {
          cy.url().should('include','menu-gestor-logistica');
          cy.get('#criar-percurso').click();
      })

      it('Criar Percurso', () => {
          cy.url().should('include','criar-percurso');
      })

      it('Inserir dados inválidos', () => {
        cy.viewport(1920, 1080);
        cy.get('[placeholder="Introduzir Armazem de Partida"').type('Arouca');
        cy.get('[placeholder="Introduzir Armazem de Chegada"]').type('Porto');
        cy.get('[placeholder="Introduzir distancia do percurso"]').type('20');
        cy.get('[placeholder="Introduzir tempo do percurso"]').type('1441');
        cy.get('[placeholder="Introduzir Energia Gasta"]').type('20');
        cy.get('[placeholder="Introduzir tempo extra"]').type('20');
        cy.get('button').click();
        cy.url().should('include','criar-percurso');
    })

    it('Verificar que não existe na listagem de Percursos', () => {
        cy.visit('/menu-gestor-logistica');
        cy.visit('/listar-percursos');
        cy.get('table').get('td').should('not.contains.text', 'Arouca');
        cy.get('table').get('td').should('not.contains.text', 'Porto');
    })

})

describe('Criar Percurso com Energia Gasta inválida e verificar que não está presente na Listagem', () => {
    it('Login', () => {
        cy.visit('/')
        cy.url().should('includes','login');
        cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("logistica");
        cy.get('button').click();
      })

      it('Menu Gestor Logística', () => {
          cy.url().should('include','menu-gestor-logistica');
          cy.get('#criar-percurso').click();
      })

      it('Criar Percurso', () => {
          cy.url().should('include','criar-percurso');
      })

      it('Inserir dados inválidos', () => {
        cy.viewport(1920, 1080);
        cy.get('[placeholder="Introduzir Armazem de Partida"').type('Porto');
        cy.get('[placeholder="Introduzir Armazem de Chegada"]').type('Trofa');
        cy.get('[placeholder="Introduzir distancia do percurso"]').type('20');
        cy.get('[placeholder="Introduzir tempo do percurso"]').type('20');
        cy.get('[placeholder="Introduzir Energia Gasta"]').type('100001');
        cy.get('[placeholder="Introduzir tempo extra"]').type('20');
        cy.get('button').click();
        cy.url().should('include','criar-percurso');
    })

    it('Verificar que não existe na listagem de Percursos', () => {
        cy.visit('/menu-gestor-logistica');
        cy.visit('/listar-percursos');
        cy.get('table').get('td').should('not.contains.text', 'Porto');
        cy.get('table').get('td').should('not.contains.text', 'Trofa');
    })

})

describe('Criar Percurso com Distância inválida e verificar que não está presente na Listagem', () => {
    it('Login', () => {
        cy.visit('/')
        cy.url().should('includes','login');
        cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("logistica");
        cy.get('button').click();
      })

      it('Menu Gestor Logística', () => {
          cy.url().should('include','menu-gestor-logistica');
          cy.get('#criar-percurso').click();
      })

      it('Criar Percurso', () => {
          cy.url().should('include','criar-percurso');
      })

      it('Inserir dados inválidos', () => {
        cy.viewport(1920, 1080);
        cy.get('[placeholder="Introduzir Armazem de Partida"').type('Arouca');
        cy.get('[placeholder="Introduzir Armazem de Chegada"]').type('Trofa');
        cy.get('[placeholder="Introduzir distancia do percurso"]').type('100001');
        cy.get('[placeholder="Introduzir tempo do percurso"]').type('20');
        cy.get('[placeholder="Introduzir Energia Gasta"]').type('20');
        cy.get('[placeholder="Introduzir tempo extra"]').type('20');
        cy.get('button').click();
        cy.url().should('include','criar-percurso');
    })

    it('Verificar que não existe na listagem de Percursos', () => {
        cy.visit('/menu-gestor-logistica');
        cy.visit('/listar-percursos');
        cy.get('table').get('td').should('not.contains.text', 'Arouca');
        cy.get('table').get('td').should('not.contains.text', 'Trofa');
    })

})

describe('Criar Percurso com Tempo Extra inválida e verificar que não está presente na Listagem', () => {
    it('Login', () => {
        cy.visit('/')
        cy.url().should('includes','login');
        cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("logistica");
        cy.get('button').click();
      })

      it('Menu Gestor Logística', () => {
          cy.url().should('include','menu-gestor-logistica');
          cy.get('#criar-percurso').click();
      })

      it('Criar Percurso', () => {
          cy.url().should('include','criar-percurso');
      })

      it('Inserir dados inválidos', () => {
        cy.viewport(1920, 1080);
        cy.get('[placeholder="Introduzir Armazem de Partida"').type('Porto');
        cy.get('[placeholder="Introduzir Armazem de Chegada"]').type('Arouca');
        cy.get('[placeholder="Introduzir distancia do percurso"]').type('20');
        cy.get('[placeholder="Introduzir tempo do percurso"]').type('20');
        cy.get('[placeholder="Introduzir Energia Gasta"]').type('20');
        cy.get('[placeholder="Introduzir tempo extra"]').type('1441');
        cy.get('button').click();
        cy.url().should('include','criar-percurso');
    })

    it('Verificar que não existe na listagem de Percursos', () => {
        cy.visit('/menu-gestor-logistica');
        cy.visit('/listar-percursos');
        cy.get('table').get('td').should('not.contains.text', 'Porto');
        cy.get('table').get('td').should('not.contains.text', 'Arouca');
    })

})
  