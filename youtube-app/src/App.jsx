import { useState, useEffect, useMemo } from 'react';
import './App.css';
import { useTable } from 'react-table';
import axios from 'axios';
import Login from './components/Login';
import Register from './components/Register';

function App() {
  const [videos, setVideos] = useState([]);
  const [videoData, setVideoData] = useState({title:"",url:"",description:""});
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [accessToken, setAccessToken] = useState('');
  const [showLogin, setShowLogin] = useState(false);
  const [showRegister, setShowRegister] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const columns = useMemo(() => [
    { Header: "Id", accessor: "id" },
    { Header: "Video Title", accessor: "title" },
    { Header: "Video URL", accessor: "url" },
    { Header: "Video Description", accessor: "description" },
  ], []);

  const data = useMemo(() => videos, [videos]);
  const { getTableProps, getTableBodyProps, headerGroups, rows, prepareRow } = useTable({ columns, data });

  const openPopup = () => {
    setIsModalOpen(true);
  };

  const closePopup = () => {
    setIsModalOpen(false);
  }

  const getVideos = () => {
    axios.get("http://139.180.186.162:8080/api/videos/all")
      .then((res) => {
        setVideos(res.data);
      })
      .catch((error) => {
        console.error("Error fetching videos:", error);
      });
  };

  const setData = (e) => {
    setVideoData({...videoData,[e.target.name]:e.target.value})
  }

  const handleSubmit = async(e) => {
    e.preventDefault();
    axios.post("http://139.180.186.162:8080/api/videos/post",videoData)
      .then((res) => {
        console.log(res.data);
      })
      .catch((error) => {
        console.error("Error fetching videos:", error);
      });
  };

  useEffect(() => {
    getVideos();
  }, []);

  const handleLogin = (token) => {
    setAccessToken(token);
    setIsLoggedIn(true);
    setShowLogin(false);
    // Optionally, you can fetch videos again or any other action after login
    getVideos();
  };

  const handleRegister = () => {
    setShowRegister(false);
    setShowLogin(true);
  };

  return (
    <div className='container'>
      <header className='header'>
        <h3>YouTube Share App using React JS, Spring Boot & MySQL</h3>
        {!isLoggedIn ? (
          <div>
            <button onClick={() => setShowLogin(true)}>Login</button>
            <button onClick={() => setShowRegister(true)}>Register</button>
          </div>
        ) : (
          <button onClick={() => { setIsLoggedIn(false); setAccessToken(''); }}>Logout</button>
        )}
      </header>
      {showLogin && <Login onLogin={handleLogin} />}
      {showRegister && <Register onRegister={handleRegister} />}
      <div className='searchbox'>
        <input className='inputsearch' type="search" name="searchinput" id="searchinput" placeholder='Search Video Here' />
        {isLoggedIn && <button className='addBtn' onClick={openPopup}>Share</button>}
      </div>
      {isLoggedIn && isModalOpen && (
        <div className='addeditPopup'>
        <span className='closeBtn' onClick={closePopup}>&times;</span>
        <h4>Video Details</h4>
        {/* Add form elements here */}
        <div className='popupdiv'>
          <label htmlFor="title">Video Title</label><br></br>
          <input className='popupinput' value={videoData.name} onChange={setData} type="text" name="title" id="title" />
        </div>
        <div className='popupdiv'>
          <label htmlFor="url">Video URL</label><br></br>
          <input className='popupinput' value={videoData.name} onChange={setData} type="text" name="url" id="url" />
        </div>
        <div className='popupdiv'>
          <label htmlFor="description">Video Description</label><br></br>
          <input className='popupinput' value={videoData.name} onChange={setData} type="text" name="description" id="description" />
        </div>
        <button className='addVideo' onClick={handleSubmit}>Share Video</button>
        </div>
      )}
      <table className='table' {...getTableProps()}>
        <thead>
          {headerGroups.map((headerGroup) => (
            <tr {...headerGroup.getHeaderGroupProps()} key={headerGroup.id}>
              {headerGroup.headers.map((column) => (
                <th {...column.getHeaderProps()} key={column.id}>{column.render("Header")}</th>
              ))}
            </tr>
          ))}
        </thead>
        <tbody {...getTableBodyProps()}>
          {rows.map((row) => {
            prepareRow(row);
            return (
              <tr {...row.getRowProps()} key={row.id}>
                {row.cells.map((cell) => (
                  <td {...cell.getCellProps()} key={cell.column.id}>
                    {cell.render('Cell')}
                  </td>
                ))}
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
}

export default App;
