
import 'reflect-metadata';

import * as sinon from 'sinon';
import { Response, Request, NextFunction } from 'express';
import { Container, Service } from 'typedi';
import { Result } from '../src/core/logic/Result';
import ICamiaoService from "../src/services/IServices/ICamiaoService";
import CamiaoController from "../src/controllers/camiaoController";
import {ICamiaoDTO} from '../src/dto/ICamiaoDTO';
import {CamiaoMap} from '../src/mappers/CamiaoMap';
import {Camiao} from '../src/domain/camiao';
import { doesNotMatch } from 'assert';
import { Logger } from 'winston';
import { dadosCamiao } from '../src/domain/dadosCamiao';
describe('Camiao Map', function () {
    const sandbox = sinon.createSandbox();
    let body = { "id":"123",
        "matricula":'AA-00-00',
    "caracteristica": "eTruck01",
     "tara": 7500 ,
     "capacidade" : 4300 ,
     "carga" : 80 ,
     "autonomia" : 100 ,
     "tempo" : 60  };

     
	beforeEach(function() {
		
		
    })

	afterEach(function() {
		sandbox.restore();
	});


it('Camiao Map Unit Test', async function name() {
    let body1 = { "id":"123",
		"matricula":'AA-00-01',
		"caracteristica": "eTruck01",
		"tara": 7500 ,
		 "capacidade" : 4300 ,
		 "carga" : 80 ,
		 "autonomia" : 100 ,
		 "tempo" : 60  };

         
        
        let value = body1 as ICamiaoDTO;
        

        let camiao= await Camiao.create(value);
        
        /*
       
        let camiaoDTO=  CamiaoMap.toDTO( CamiaoMap.toDomain(camiao.getValue()) ) as ICamiaoDTO;
     
        */
        sinon.assert.match(body1,CamiaoMap.toDomain(camiao));
        //sinon.assert.match(body1,CamiaoMap.toPersistence(camiao.getValue()));
    


    })

});