import React, { useState } from "react";
import Sidebar from "../Sidebar";
import { Line, Pie } from "react-chartjs-2";
import {
  Chart as ChartJS,
  CategoryScale,  
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
  ArcElement
} from 'chart.js';
import ChartDataLabels from 'chartjs-plugin-datalabels'; // 플러그인 추가
import {
  MenuItem,
  Select,
  FormControl,
  InputLabel,
  Box,
  Grid,
  Typography,
} from "@mui/material";
import "./FundingStatistics.css";

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
  ArcElement,
  ChartDataLabels // 퍼센트 표시를 위한 플러그인 등록
);

const FundingStatistics = () => {
  const [selectedMenu, setSelectedMenu] = useState("통계");
  const [selectedFunding, setSelectedFunding] = useState(null);

  // 더미 펀딩 데이터
  const fundingList = [
    { id: 1, title: "펀딩 A" },
    { id: 2, title: "펀딩 B" },
    { id: 3, title: "펀딩 C" }
  ];

  // 펀딩별 더미 통계 데이터
  const fundingData = {
    1: {
      lineData: {
        labels: ["10월 1일", "10월 2일", "10월 3일", "10월 4일"],
        datasets: [
          {
            label: "펀딩 A 현황",
            data: [100000, 200000, 150000, 50000], // 일별 모금액
            fill: true,
            backgroundColor: "rgba(102, 178, 255, 0.2)",
            borderColor: "rgba(102, 178, 255, 1)",
          },
        ],
      },
      maleData: {
        "60+": 10,
        "20s": 15,
        "30s": 20,
        "40s": 25,
        "50s": 30
      },
      femaleData: {
        "60+": 12,
        "20s": 18,
        "30s": 22,
        "40s": 28,
        "50s": 35
      },
      participants: [
        { name: "김수로", amount: 200000 },
        { name: "박영수", amount: 150000 },
        { name: "이준영", amount: 100000 }
      ]
    },
    2: {
      lineData: {
        labels: ["10월 1일", "10월 2일", "10월 3일", "10월 4일"],
        datasets: [
          {
            label: "펀딩 B 현황",
            data: [50000, 150000, 250000, 0], // 일별 모금액
            fill: true,
            backgroundColor: "rgba(255, 159, 64, 0.2)",
            borderColor: "rgba(255, 159, 64, 1)",
          },
        ],
      },
      maleData: {
        "60+": 15,
        "20s": 20,
        "30s": 25,
        "40s": 30,
        "50s": 35
      },
      femaleData: {
        "60+": 14,
        "20s": 19,
        "30s": 24,
        "40s": 29,
        "50s": 34
      },
      participants: [
        { name: "최민수", amount: 250000 },
        { name: "이준기", amount: 200000 },
        { name: "홍길동", amount: 50000 }
      ]
    },
    3: {
      lineData: {
        labels: ["10월 1일", "10월 2일", "10월 3일", "10월 4일"],
        datasets: [
          {
            label: "펀딩 C 현황",
            data: [80000, 180000, 280000, 380000], // 일별 모금액
            fill: true,
            backgroundColor: "rgba(153, 102, 255, 0.2)",
            borderColor: "rgba(153, 102, 255, 1)",
          },
        ],
      },
      maleData: {
        "60+": 20,
        "20s": 25,
        "30s": 30,
        "40s": 35,
        "50s": 40
      },
      femaleData: {
        "60+": 22,
        "20s": 28,
        "30s": 32,
        "40s": 36,
        "50s": 38
      },
      participants: [
        { name: "박지성", amount: 300000 },
        { name: "손흥민", amount: 180000 },
        { name: "이청용", amount: 80000 }
      ]
    }
  };

  const handleFundingChange = (event) => {
    setSelectedFunding(event.target.value);
  };

  const selectedFundingData = selectedFunding ? fundingData[selectedFunding] : null;

  return (
    <div className="funding-statistics-container">
      <Sidebar selectedMenu={selectedMenu} setSelectedMenu={setSelectedMenu} />

      <div className="funding-statistics-content">
        <Typography variant="h4" className="funding-statistics-title">
          {selectedFunding ? `펀딩 ${fundingList.find(f => f.id === selectedFunding).title} 통계` : "펀딩 통계입니다."}
        </Typography>

        <div className="funding-statistics-buttons">
          {/* 펀딩 선택 드롭다운 */}
          <FormControl variant="outlined" className="dropdown">
            <InputLabel>펀딩 선택</InputLabel>
            <Select value={selectedFunding || ""} onChange={handleFundingChange} label="펀딩 선택">
              {fundingList.map((funding) => (
                <MenuItem key={funding.id} value={funding.id}>
                  {funding.title}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </div>

        {selectedFundingData ? (
          <Grid container spacing={4} className="funding-statistics-main">
            <Grid item xs={12} md={8}>
              <Box className="chart-container">
                <Typography variant="h6">기간통계</Typography>
                <Line data={selectedFundingData.lineData} />
              </Box>
            </Grid>

            <Grid item xs={12} md={4}>
              <Box className="user-statistics">
                <Typography variant="h6">유저개인통계</Typography>
                <div className="user-list">
                  <ul>
                    {selectedFundingData.participants.map((participant, index) => (
                      <li key={index}>
                        <span>{participant.name}</span> <span>{participant.amount.toLocaleString()}원</span>
                      </li>
                    ))}
                  </ul>
                </div>
              </Box>
            </Grid>

            <Grid item xs={12}>
              <Box className="gender-statistics">
                <Box className="gender-stat-item">
                  <Typography variant="h6">남성 연령별 통계</Typography>
                  <Pie 
                    data={{
                      labels: Object.keys(selectedFundingData.maleData),
                      datasets: [{
                        data: Object.values(selectedFundingData.maleData),
                        backgroundColor: ["#FF6384", "#36A2EB", "#FFCE56", "#4BC0C0", "#9966FF"],
                      }],
                    }}
                    options={{
                      plugins: {
                        datalabels: {
                          formatter: (value, context) => {
                            const total = context.chart.data.datasets[0].data.reduce((a, b) => a + b, 0);
                            const percentage = (value / total * 100).toFixed(2) + "%";
                            return percentage;
                          },
                          color: '#fff',
                          font: {
                            weight: 'bold'
                          }
                        }
                      }
                    }}
                  />
                </Box>
                <Box className="gender-stat-item">
                  <Typography variant="h6">여성 연령별 통계</Typography>
                  <Pie 
                    data={{
                      labels: Object.keys(selectedFundingData.femaleData),
                      datasets: [{
                        data: Object.values(selectedFundingData.femaleData),
                        backgroundColor: ["#FF6384", "#36A2EB", "#FFCE56", "#4BC0C0", "#9966FF"],
                      }],
                    }}
                    options={{
                      plugins: {
                        datalabels: {
                          formatter: (value, context) => {
                            const total = context.chart.data.datasets[0].data.reduce((a, b) => a + b, 0);
                            const percentage = (value / total * 100).toFixed(2) + "%";
                            return percentage;
                          },
                          color: '#fff',
                          font: {
                            weight: 'bold'
                          }
                        }
                      }
                    }}
                  />
                </Box>
              </Box>
            </Grid>
          </Grid>
        ) : (
          <Typography variant="h6" className="no-funding-message">
            펀딩을 선택해 주세요.
          </Typography>
        )}
      </div>
    </div>
  );
};

export default FundingStatistics;
