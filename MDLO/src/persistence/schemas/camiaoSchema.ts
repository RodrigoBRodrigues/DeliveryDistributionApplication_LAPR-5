import { ICamiaoPersistence } from '../../dataschema/ICamiaoPersistence';
import mongoose from 'mongoose';

const CamiaoSchema = new mongoose.Schema(
  {
    domainId: {
      type: String,
      unique: true
    },

    matricula:{
      type: String,
      required: [true,'Por favor coloque a matricula do camião'],
      unique:true
    },

    caracteristica: {
      type: String,
      required: [true, 'Por favor coloque a caracteristica do camião'],
      index: true,
    },

    tara: {
      type: Number,
      required: [true, 'Por favor coloque a tara'],
      index: true,
    },

    capacidade: {
      type: Number,
      required: [true,'Por favor coloque a capacidade'],
      index: true,
    },

    carga: {
        type: Number,
        required: [true,'Por favor coloque a carga'],
        index: true,
      },

      autonomia: {
        type: Number,
        required: [true,'Por favor coloque a autonomia'],
        index: true,
      },

      tempo: {
        type: Number,
        required: [true,'Por favor coloque o tempo'],
        index: true,
      },
      ativo:{
        type: Boolean,
        required:[true,'Por favor coloque o estado'],
        index:true,
      }
  },
  { timestamps: true },
);

export default mongoose.model<ICamiaoPersistence & mongoose.Document>('Camiao', CamiaoSchema);
