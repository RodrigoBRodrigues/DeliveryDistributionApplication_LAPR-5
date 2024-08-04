

describe('Login -> Menu Gestor Camião -> Listar Camiões', () => {
    it('Login', () => {
      cy.visit('/')
      cy.url().should('includes','login');
      cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("frota");
      cy.get('button').click();
    })
    it('Menu Gestor Frota', () => {
        cy.url().should('include','menu-gestor-frota');
        cy.get('#listar-camioes').click();
    })
    it('Listar Camiões', () => {
        cy.url().should('include','listar-camioes');
    })
})


describe('Criar Camiões válidos e verificar que eles estão presente na Listagem', () => {
    it('Login', () => {
        cy.visit('/')
        cy.url().should('includes','login');
        cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("frota");
        cy.get('button').click();
      })

      it('Menu Gestor Frota', () => {
          cy.url().should('include','menu-gestor-frota');
          cy.get('#criar-camiao').click();
      })

      it('Criar Camião', () => {
          cy.url().should('include','criar-camiao');
      })

      it('Inserir dados válidos', () => {
        cy.viewport(1920, 1080);
        cy.get('[placeholder="Introduzir matricula"').type('BB-99-99');
        cy.get('[placeholder="Introduzir caracteristica"]').type('eTruck01');
        cy.get('[placeholder="Introduzir tara"]').type('8000');
        cy.get('[placeholder="Introduzir capacidade"]').type('3500');
        cy.get('[placeholder="Introduzir carga"]').type('4000');
        cy.get('[placeholder="Introduzir autonomia"]').type('80');
        cy.get('[placeholder="Introduzir tempo"]').type('120');
        cy.get('button').click();
        cy.on('window:alert',(t)=>{
            expect(t).to.contains('Camião Criado!');
         })
         cy.url().should('include','criar-camiao');
    })
    

    it('Verificar que existe na listagem de Camiões e criar outro Camião', () => {
        cy.visit('/menu-gestor-frota');
        cy.visit('/listar-camioes');
        cy.get('table').get('td').should('contains.text', 'BB-99-99');
        cy.get('table').get('td').should('contains.text', 'eTruck01');
        cy.get('table').get('td').should('contains.text', '8000');
        cy.visit('/menu-gestor-frota');
        cy.visit('/criar-camiao');
        cy.get('[placeholder="Introduzir matricula"').type('AA-50-50');
        cy.get('[placeholder="Introduzir caracteristica"]').type('eTruck01');
        cy.get('[placeholder="Introduzir tara"]').type('8000');
        cy.get('[placeholder="Introduzir capacidade"]').type('3500');
        cy.get('[placeholder="Introduzir carga"]').type('4000');
        cy.get('[placeholder="Introduzir autonomia"]').type('80');
        cy.get('[placeholder="Introduzir tempo"]').type('120');
        cy.get('button').click();
        cy.on('window:alert',(t)=>{
            expect(t).to.contains('Camião Criado!');
         })
        cy.visit('/menu-gestor-frota');
        cy.visit('/listar-camioes');
        cy.get('table').get('td').should('contains.text', 'CC-99-99');
        cy.get('table').get('td').should('contains.text', 'eTruck01');
        cy.get('table').get('td').should('contains.text', '8000');
    })
})

describe('Filtro Matricula Listagem de Camiões', () => {
    it('Login', () => {
        cy.visit('/')
        cy.url().should('includes','login');
        cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("frota");
        cy.get('button').click();
      })
      it('Menu Gestor Frota', () => {
          cy.url().should('include','menu-gestor-frota');
          cy.get('#listar-camioes').click();
      })
      it('Listar Camiões', () => {
          cy.url().should('include','listar-camioes');
      })
    


    it('Verificar que o Filtro Id funciona', () => {
        cy.get('table').get('[placeholder="Procure Camião por matrícula"').type('AA');
        cy.get('table').get('td').should('contains.text', 'AA');
        cy.get('table').get('td').should('not.contains.text', 'BB');
        cy.get('table').get('td').should('not.contains.text', 'CC');

    })

    

}) 

describe('Inibir camiões', () => {
  it('Login', () => {
      cy.visit('/')
      cy.url().should('includes','login');
      cy.get('[placeholder="Introduzir Nome de Utilizador"]').type("frota");
      cy.get('button').click();
    })
    it('Menu Gestor Frota', () => {
        cy.url().should('include','menu-gestor-frota');
        cy.get('#listar-camioes').click();
    })
    it('Listar Camiões', () => {
        cy.url().should('include','listar-camioes');
    })
  


  it('Desativar o camião ', () => {
    
    let myString;
  
cy.get('table').get('button').invoke('text').then((text) => {
  myString = text;
  console.log("AAAAAAAAAAAAAAAA"+ myString)
  if(myString.includes('Não')){
    cy.get('table').get('button').click({multiple:true});
    cy.get('table').get('button').should('have.css', 'background-color', 'rgb(4, 170, 109)');
  }else{
    cy.get('table').get('button').click({multiple:true});
    cy.get('table').get('button').should('have.css', 'background-color', 'rgb(255, 0, 0)');
  }

});
    

    
   

  })

  

}) 