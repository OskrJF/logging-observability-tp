/* src/App.js - Zero Dependency Version */
import React, { useState } from 'react';

// We do NOT import tracing.js or @opentelemetry/* here to prevent crashes.

function App() {
  const [products, setProducts] = useState([]);

  // Helper to generate a fake W3C Trace Header manually
  // This tricks the backend into thinking a real tracer is running
  const generateTraceHeader = () => {
    // Generate random hex strings
    const traceId = [...Array(32)].map(() => Math.floor(Math.random() * 16).toString(16)).join('');
    const spanId = [...Array(16)].map(() => Math.floor(Math.random() * 16).toString(16)).join('');
    return `00-${traceId}-${spanId}-01`;
  };

  const fetchProducts = async () => {
    console.log("Fetching...");
    try {
        const response = await fetch('http://localhost:8080/api/products', {
           headers: {
             // Manual Trace Injection without libraries
             'traceparent': generateTraceHeader() 
           }
        });
        const data = await response.json();
        setProducts(data);
    } catch (err) {
        console.error(err);
    }
  };

  const addProduct = async () => {
    const newProduct = { 
        id: "P" + Math.floor(Math.random() * 10000), 
        name: "Manual Trace Item", 
        price: 99.99, 
        expirationDate: "2025-12-31" 
    };
    
    try {
        await fetch('http://localhost:8080/api/products', {
            method: 'POST',
            headers: { 
                'Content-Type': 'application/json',
                // Manual Trace Injection without libraries
                'traceparent': generateTraceHeader()
            },
            body: JSON.stringify(newProduct)
        });
        alert("Product Added!");
    } catch (err) {
        console.error(err);
    }
  };

  return (
    <div style={{ padding: '2rem' }}>
      <h1>Store Frontend (Simulation Mode)</h1>
      <button onClick={fetchProducts} style={{ marginRight: '10px' }}>Load Products</button>
      <button onClick={addProduct}>Add Product</button>
      <ul>
        {products.map(p => <li key={p.id}>{p.name} - ${p.price}</li>)}
      </ul>
    </div>
  );
}

export default App;