package repositories;

import models.Operacion;
import org.apache.log4j.Logger;

import java.util.*;

public class ReversePolishNotationImpl implements ReversePolishNotation {
    final static Logger logger = Logger.getLogger(ReversePolishNotationImpl.class);

    @Override
    public double calcular(String expresion) {
        logger.info("calcular(" + expresion + ")");

        if (expresion == null || expresion.trim().isEmpty()) {
            logger.error("calcular failed: expresion vacia o null");
            throw new IllegalArgumentException("Expressio no valida");
        }

        String[] partes = expresion.split(" ");
        Stack<Double> pila = new Stack<>();
        double res;

        for (String parte : partes){
            if (!Operador(parte)){
                pila.push(Double.parseDouble(parte));
                logger.info("Parte numero: " + parte + " -> push");
            }
            else{
                double num2 = pila.pop();
                double num1 = pila.pop();
                res = Operacion(num1,num2,parte);
                pila.push(res);
                logger.info("Parte operador: " + parte + " -> resultado: " + res);
            }
        }
        double resultadoFinal = pila.pop();
        logger.info("calcular completado: " + resultadoFinal);
        return resultadoFinal;
    }

    private boolean Operador(String parte) {
        logger.info("Operador(" + parte + ")");

        boolean resultado;
        if (parte.equals("+") || parte.equals("-") || parte.equals("/") || parte.equals("*")) {
            resultado = true;
        } else {
            resultado = false;
        }
        logger.info("Operador completed: " + parte + " -> " + resultado);
        return resultado;
    }

    private double Operacion(Double num1, Double num2, String operador) {
        logger.info("Operacion(" + num1 + ", " + num2 + ", " + operador + ")");

        double res;

        if (operador.equals("+")) {
            res = num1 + num2;
        }
        else if (operador.equals("-")) {
            res = num1 - num2;
        }
        else if (operador.equals("/")) {
            res = num1 / num2;
        }
        else {
            res = num1 * num2;
        }

        logger.info("Operacion completed: " + res);
        return res;
    }
}

