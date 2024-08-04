import 'reflect-metadata';

import * as sinon from 'sinon';
import { Response, Request, NextFunction } from 'express';
import { Container, Service } from 'typedi';
import { Result } from '../src/core/logic/Result';
import ICamiaoService from "../src/services/IServices/ICamiaoService";
import CamiaoController from "../src/controllers/camiaoController";
import {ICamiaoDTO} from '../src/dto/ICamiaoDTO';
import {Camiao} from '../src/domain/camiao';
import { doesNotMatch } from 'assert';
import { Logger } from 'winston';
import CamiaoService from '../src/services/camiaoService';
import ICamiaoRepo from '../src/services/IRepos/ICamiaoRepo';
import { dadosCamiao } from '../src/domain/dadosCamiao';


describe('Camiao ', function () {
	const sandbox = sinon.createSandbox();
    let body = { "matricula":'AA-00-00',
    "caracteristica": "eTruck01",
     "tara": 7500 ,
     "capacidade" : 4300 ,
     "carga" : 80 ,
     "autonomia" : 100 ,
     "tempo" : 60  };

    let  camiaoCriado=Camiao.create(body as ICamiaoDTO);
	beforeEach(function() {
		
		
    })

	afterEach(function() {
		sandbox.restore();
	});

    it('Camiao get tests',async function name() {
        
        sinon.assert.match(body.matricula,camiaoCriado.getValue().matricula.value)
        sinon.assert.match(body.caracteristica,camiaoCriado.getValue().caracteristica)
        sinon.assert.match(body.tara,camiaoCriado.getValue().dados.tara)
        sinon.assert.match(body.capacidade,camiaoCriado.getValue().dados.capacidade)
        sinon.assert.match(body.carga,camiaoCriado.getValue().dados.carga)
        sinon.assert.match(body.autonomia,camiaoCriado.getValue().dados.autonomia)
        sinon.assert.match(body.tempo,camiaoCriado.getValue().dados.tempo)
        
        
    })

    it('Camiao set tests',async function name(){
        let body2 = { "matricula":'AA-00-00',
        "caracteristica": "eTruck02",
         "tara": 7501 ,
         "capacidade" : 4301 ,
         "carga" : 81 ,
         "autonomia" : 101 ,
         "tempo" : 61  };
        
         const dadosCamiaoProps =new dadosCamiao({tara:body2.tara,
            capacidade:body2.capacidade,
            carga:body2.carga,
            autonomia:body2.autonomia,
            tempo:body2.tempo});
        camiaoCriado.getValue().caracteristica=body2.caracteristica;
        camiaoCriado.getValue().dados=dadosCamiaoProps;
       

        sinon.assert.match(camiaoCriado.getValue().caracteristica,body2.caracteristica);
        sinon.assert.match(camiaoCriado.getValue().dados.tara,body2.tara);
        sinon.assert.match(camiaoCriado.getValue().dados.capacidade,body2.capacidade);
        sinon.assert.match(camiaoCriado.getValue().dados.carga,body2.carga);
        sinon.assert.match(camiaoCriado.getValue().dados.autonomia,body2.autonomia);
        sinon.assert.match(camiaoCriado.getValue().dados.tempo,body2.tempo);
    })
})