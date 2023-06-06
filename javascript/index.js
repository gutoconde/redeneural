const brain = require('brain.js')

const config = {
    //binaryThresh: 0.5,
    hiddenLayers: [2], // array of ints for the sizes of the hidden layers in the network
    activation: 'sigmoid', // supported activation types: ['sigmoid', 'relu', 'leaky-relu', 'tanh'],
    //leakyReluAlpha: 0.01, // supported for activation type 'leaky-relu'
    learningRate: 0.1,
    iterations: 100000,
    errorThresh: 0.001,
  };
  
  // create a simple feed forward neural network with backpropagation
  const net = new brain.NeuralNetwork(config);
  
  net.train([
    { input: [0, 0], output: [0] },
    { input: [0, 1], output: [1] },
    { input: [1, 0], output: [1] },
    { input: [1, 1], output: [0] },
  ]);
  
var output = net.run([0, 0]);
console.log("0 0 - " + output);
output = net.run([0, 1]);
console.log("0 1 - " + output);
output = net.run([1, 0]);
console.log("1 0 - " + output);
output = net.run([1, 1]);
console.log("1 1 - " + output);
  