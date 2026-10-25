import { useEffect, useState } from "react";
import axios from "axios";

function App() {
  const [memberId, setMemberId] = useState("");

  const [summary, setSummary] = useState(null);

  const [stats, setStats] = useState(null);

  const [alerts, setAlerts] = useState([]);

  useEffect(() => {
    loadDashboardStats();

    loadAlerts();
  }, []);

  const loadDashboardStats = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/api/dashboard/stats",
      );

      setStats(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const loadAlerts = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/alerts");

      setAlerts(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const searchMember = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/members/${memberId}/summary`,
      );

      setSummary(response.data);
    } catch (error) {
      alert("Member not found");
    }
  };

  const getBadgeClass = (level) => {
    switch (level) {
      case "CRITICAL":
        return "bg-danger";

      case "HIGH":
        return "bg-warning text-dark";

      case "WARNING":
        return "bg-info";

      default:
        return "bg-secondary";
    }
  };

  return (
    <div className="container mt-4">
      <h1 className="mb-4 text-primary">Stop Loss Monitoring Dashboard</h1>

      {stats && (
        <div className="row mb-4">
          <div className="col-md-3">
            <div className="card bg-primary text-white">
              <div className="card-body">
                <h6>Groups</h6>

                <h2>{stats.groups}</h2>
              </div>
            </div>
          </div>

          <div className="col-md-3">
            <div className="card bg-success text-white">
              <div className="card-body">
                <h6>Members</h6>

                <h2>{stats.members}</h2>
              </div>
            </div>
          </div>

          <div className="col-md-3">
            <div className="card bg-warning">
              <div className="card-body">
                <h6>Lasers</h6>

                <h2>{stats.lasers}</h2>
              </div>
            </div>
          </div>

          <div className="col-md-3">
            <div className="card bg-danger text-white">
              <div className="card-body">
                <h6>Alerts</h6>

                <h2>{stats.alerts}</h2>
              </div>
            </div>
          </div>
        </div>
      )}

      <div className="card p-4 shadow-sm">
        <h4 className="mb-3">Member Search</h4>

        <div className="row">
          <div className="col-md-8">
            <input
              type="text"
              className="form-control"
              placeholder="Enter Member ID"
              value={memberId}
              onChange={(e) => setMemberId(e.target.value)}
            />
          </div>

          <div className="col-md-4">
            <button className="btn btn-primary w-100" onClick={searchMember}>
              Search
            </button>
          </div>
        </div>
      </div>

      {summary && (
        <div className="card mt-4 p-4 shadow-sm">
          <h3>Member Summary</h3>

          <hr />

          <table className="table">
            <tbody>
              <tr>
                <th>Member ID</th>
                <td>{summary.memberId}</td>
              </tr>

              <tr>
                <th>Group</th>
                <td>{summary.groupId}</td>
              </tr>

              <tr>
                <th>Spend</th>
                <td>${summary.spend}</td>
              </tr>

              <tr>
                <th>Deductible</th>
                <td>${summary.deductible}</td>
              </tr>

              <tr>
                <th>Utilization</th>
                <td>{summary.utilizationPercent}%</td>
              </tr>

              <tr>
                <th>Alert Level</th>
                <td>
                  {summary.alertLevel ? (
                    <span
                      className={`badge ${getBadgeClass(summary.alertLevel)}`}
                    >
                      {summary.alertLevel}
                    </span>
                  ) : (
                    "None"
                  )}
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      )}

      <div className="card mt-4 p-4 shadow-sm">
        <h3>Active Alerts</h3>

        <hr />

        {alerts.length === 0 ? (
          <div className="alert alert-success">No active alerts found.</div>
        ) : (
          <table className="table table-striped">
            <thead>
              <tr>
                <th>Member</th>

                <th>Group</th>

                <th>Spend</th>

                <th>Deductible</th>

                <th>Utilization</th>

                <th>Alert</th>
              </tr>
            </thead>

            <tbody>
              {alerts.map((alert, index) => (
                <tr key={index}>
                  <td>{alert.memberId}</td>

                  <td>{alert.groupId}</td>

                  <td>{alert.spend}</td>

                  <td>{alert.deductible}</td>

                  <td>{alert.utilizationPercent}%</td>

                  <td>
                    <span
                      className={`badge ${getBadgeClass(alert.alertLevel)}`}
                    >
                      {alert.alertLevel}
                    </span>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
}

export default App;
