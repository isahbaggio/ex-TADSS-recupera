# Relatório — Jantar dos Filósofos

## O que foi feito

O projeto foi separado em 3 classes:

- `Garfo`: recurso compartilhado (um lock por garfo).
- `Filosofo`: thread que alterna entre pensar, ficar com fome e comer.
- `Mesa`: cria os filósofos e roda os cenários.

## Cenários implementados

### 1) Solução ingênua

Cada filósofo pega primeiro o garfo da esquerda e depois o da direita.

Esse é o cenário clássico onde pode acontecer deadlock: todos pegam um garfo e ficam esperando o outro para sempre.

### 2) Solução com hierarquia

Foi aplicada a técnica de hierarquia de recursos:

- todos pegam esquerdo -> direito,
- **exceto o último filósofo**, que pega direito -> esquerdo.

Com isso a espera circular é quebrada e o impasse geral deixa de acontecer.

## Sobre starvation

Nesta implementação a fome infinita não apareceu nos testes curtos.

Em sistemas reais, para reforçar justiça, pode-se usar `Semaphore` justo, monitor com fila, ou políticas explícitas de prioridade.

## Como executar

```bash
javac src/*.java
java -cp src Mesa
```
