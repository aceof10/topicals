-- Insert data into author table
INSERT INTO author (name, bio) VALUES
('Alice Johnson', 'Alice is a seasoned software engineer with a passion for writing educational content on programming and software development.'),
('Bob Smith', 'Bob is a computer scientist specializing in algorithms and data structures. He enjoys sharing his knowledge through detailed tutorials.'),
('Carol White', 'Carol has a background in machine learning and artificial intelligence, contributing articles on these advanced topics.');

-- Insert data into field table
INSERT INTO field (name, description, author_id) VALUES
('Computer Science', 'The study of computers and computational systems.', (SELECT id FROM author WHERE name = 'Alice Johnson')),
('Data Science', 'The study of data analysis and interpretation.', (SELECT id FROM author WHERE name = 'Bob Smith'));

-- Insert data into subject table
INSERT INTO subject (name, description, field_id, author_id) VALUES
('Programming', 'The process of creating software through coding.',
    (SELECT id FROM field WHERE name = 'Computer Science'), (SELECT id FROM author WHERE name = 'Alice Johnson')),
('Machine Learning', 'A subset of artificial intelligence focusing on building systems that learn from data.',
    (SELECT id FROM field WHERE name = 'Data Science'), (SELECT id FROM author WHERE name = 'Carol White'));


INSERT INTO topic (name, subject_id, author_id, content) VALUES
('Introduction to Python',
    (SELECT id FROM subject WHERE name = 'Programming'),
    (SELECT id FROM author WHERE name = 'Alice Johnson'),
    'Python is a high-level, interpreted programming language known for its easy-to-read syntax and versatility. 
    It is widely used for web development, data analysis, artificial intelligence, scientific computing, and more. 
    Python supports multiple programming paradigms, including procedural, object-oriented, and functional programming. 
    Its comprehensive standard library and the vast ecosystem of third-party packages make it a popular choice among developers of all skill levels. 
    In this topic, we will cover the fundamentals of Python, including its syntax, basic data types (such as strings, integers, and floats), 
    and control structures like loops and conditionals. You will also learn how to write simple Python programs and understand the basics 
    of functions and modules. By the end of this topic, you will have a solid foundation in Python and be prepared to tackle more advanced concepts.'
),

('For Loops in Python',
    (SELECT id FROM subject WHERE name = 'Programming'),
    (SELECT id FROM author WHERE name = 'Alice Johnson'),
    'For loops in Python are a powerful way to iterate over sequences such as lists, tuples, strings, and ranges. 
    A for loop allows you to execute a block of code repeatedly for each element in a sequence, making it an essential tool 
    for automation and data manipulation. This topic provides an in-depth exploration of for loops, including their syntax 
    and various use cases. You will learn how to use the range function to create loops that execute a specific number of times, 
    iterate through lists and dictionaries, and apply loops to complex data structures like nested lists. 
    Additionally, we will cover common loop patterns such as summing elements, finding the maximum value, 
    and filtering data based on conditions. By the end of this topic, you will understand how to effectively use 
    for loops to solve a wide range of programming challenges in Python.'
),

('Basics of Neural Networks',
    (SELECT id FROM subject WHERE name = 'Machine Learning'),
    (SELECT id FROM author WHERE name = 'Carol White'),
    'Neural networks are a foundational concept in machine learning and artificial intelligence, inspired by the structure 
    and function of the human brain. They consist of interconnected layers of nodes (or neurons) that process data in a manner 
    similar to biological neurons. This topic introduces the fundamental concepts of neural networks, including their architecture, 
    activation functions, and learning algorithms. You will learn about different types of neural networks, such as feedforward 
    neural networks, convolutional neural networks (CNNs), and recurrent neural networks (RNNs), each designed for specific types 
    of tasks like image recognition or natural language processing. The topic will also cover the training process, where a neural 
    network learns to make predictions based on input data, using techniques like backpropagation and gradient descent to minimize 
    error. By understanding these core principles, you will be well-equipped to explore more advanced neural network architectures 
    and applications in various domains.'
);