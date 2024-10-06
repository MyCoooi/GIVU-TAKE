import React, { useState } from "react";
import Sidebar from "../Sidebar"; // Assuming you have a Sidebar component
import { Line, Pie } from "react-chartjs-2"; // Chart.js components
import {
  Chart as ChartJS,
  CategoryScale,  // Register the category scale
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
  ArcElement // for Pie chart
} from 'chart.js';
import {
  MenuItem,
  Select,
  Button,
  FormControl,
  InputLabel,
  Box,
  Grid,
  Typography,
} from "@mui/material";
import "./FundingStatistics.css"; // Import the CSS file for styles

// Register the required components with ChartJS
ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
  ArcElement
);

const FundingStatistics = () => {
  const [selectedType, setSelectedType] = useState("지역펀딩");
  const [selectedMenu, setSelectedMenu] = useState("통계"); // selectedMenu state 추가

  // Example data for the charts (replace with your API data)
  const lineData = {
    labels: ["1994", "1998", "2002", "2006", "2010", "2014", "2018", "2022"],
    datasets: [
      {
        label: "펀딩 현황",
        data: [10, 25, 15, 30, 45, 35, 55, 40],
        fill: true,
        backgroundColor: "rgba(102, 178, 255, 0.2)",
        borderColor: "rgba(102, 178, 255, 1)",
      },
    ],
  };

  const pieData = {
    labels: ["여", "남"],
    datasets: [
      {
        data: [60, 40],
        backgroundColor: ["#FF6384", "#36A2EB"],
        hoverBackgroundColor: ["#FF6384", "#36A2EB"],
      },
    ],
  };

  const handleTypeChange = (event) => {
    setSelectedType(event.target.value);
  };

  return (
    <div className="funding-statistics-container">
      <Sidebar selectedMenu={selectedMenu} setSelectedMenu={setSelectedMenu} /> {/* setSelectedMenu 전달 */}

      <div className="funding-statistics-content">
        <Typography variant="h4" className="funding-statistics-title">
          펀딩 통계
        </Typography>

        <div className="funding-statistics-buttons">
        <Button variant={selectedType === "재난재해펀딩" ? "contained" : "outlined"} onClick={() => setSelectedType("재난재해펀딩")}>
            재난재해
          </Button>
          <Button variant={selectedType === "지역펀딩" ? "contained" : "outlined"} onClick={() => setSelectedType("지역펀딩")}>
            지역기부
          </Button>


          <FormControl variant="outlined" className="dropdown">
            <InputLabel>기간</InputLabel>
            <Select value={selectedType} onChange={handleTypeChange} label="기간">
              <MenuItem value="7일">7일</MenuItem>
              <MenuItem value="1개월">1개월</MenuItem>
              <MenuItem value="1년">1년</MenuItem>
            </Select>
          </FormControl>
        </div>

        <Grid container spacing={4} className="funding-statistics-main">
          <Grid item xs={12} md={8}>
            <Box className="chart-container">
              <Typography variant="h6">기간통계</Typography>
              <Line data={lineData} />
            </Box>
          </Grid>

          <Grid item xs={12} md={4}>
            <Box className="user-statistics">
              <Typography variant="h6">유저개인통계</Typography>
              <div className="user-list">
                <ul>
                  {["아무개", "아무개", "아무개"].map((name, index) => (
                    <li key={index}>
                      <span>{name}</span> <span>금액</span>
                    </li>
                  ))}
                </ul>
              </div>
            </Box>
          </Grid>

          <Grid item xs={12}>
            <Box className="gender-statistics">
              <Typography variant="h6">유저남녀통계</Typography>
              <div className="pie-charts">
                <Pie data={pieData} />
                <Pie data={pieData} />
              </div>
            </Box>
          </Grid>
        </Grid>
      </div>
    </div>
  );
};

export default FundingStatistics; 