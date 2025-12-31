<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.bengregory.JobApp.model.JobPost" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Job Posted Successfully – Job Description Portal</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

    <style>
        :root {
            --primary:    #0d9488;
            --primary-dark: #0f766e;
            --primary-light: #5eead4;
            --dark:       #0f172a;
            --gray:       #64748b;
            --light:      #f8fafc;
            --light-gray: #e2e8f0;
            --success:    #10b981;
        }

        body {
            background-color: var(--light);
            color: var(--dark);
            font-family: system-ui, -apple-system, 'Segoe UI', Roboto, sans-serif;
            line-height: 1.6;
        }

        .navbar {
            position: sticky;
            top: 0;
            z-index: 1000;
            background-color: white;
            border-bottom: 1px solid var(--light-gray);
            transition: box-shadow 0.3s ease;
        }

        .navbar.scrolled {
            box-shadow: 0 4px 16px rgba(0,0,0,0.08);
        }

        .navbar-brand {
            font-weight: 800;
            font-size: 1.5rem;
            color: var(--primary-dark) !important;
        }

        .nav-link {
            font-weight: 500;
            color: var(--gray) !important;
            transition: color 0.2s;
        }

        .nav-link:hover,
        .nav-link.active {
            color: var(--primary) !important;
        }

        .success-section {
            padding: 6rem 1rem 8rem;
            text-align: center;
        }

        .success-card {
            max-width: 720px;
            margin: 0 auto;
            border: none;
            border-radius: 1.5rem;
            overflow: hidden;
            box-shadow: 0 12px 40px rgba(0,0,0,0.1);
            background: white;
        }

        .success-header {
            background: linear-gradient(135deg, var(--success) 0%, #059669 100%);
            color: white;
            padding: 3rem 2rem 2.5rem;
        }

        .success-icon {
            font-size: 4.5rem;
            margin-bottom: 1.5rem;
        }

        .success-title {
            font-weight: 800;
            font-size: 2.2rem;
            margin-bottom: 0.75rem;
        }

        .card-body {
            padding: 2.5rem 3rem;
        }

        .job-title {
            font-size: 1.6rem;
            font-weight: 700;
            color: var(--primary-dark);
            margin-bottom: 1.25rem;
        }

        .job-meta {
            font-size: 1rem;
            color: var(--gray);
            margin-bottom: 1rem;
        }

        .tech-badge {
            background: rgba(13, 148, 136, 0.1);
            color: var(--primary-dark);
            border: 1px solid rgba(13, 148, 136, 0.25);
            padding: 0.45rem 1rem;
            border-radius: 2rem;
            font-size: 0.92rem;
            margin: 0 0.4rem 0.6rem 0;
            display: inline-block;
        }

        .btn-action {
            font-weight: 600;
            padding: 0.75rem 2rem;
            border-radius: 3rem;
            transition: all 0.25s;
        }

        .btn-primary-action {
            background: var(--primary);
            border: none;
        }

        .btn-primary-action:hover {
            background: var(--primary-dark);
            transform: translateY(-2px);
        }

        .btn-outline-action {
            border-color: var(--primary);
            color: var(--primary);
        }

        .btn-outline-action:hover {
            background: var(--primary);
            color: white;
            transform: translateY(-2px);
        }

        /* Footer */
        footer {
            background: white;
            border-top: 1px solid var(--light-gray);
            padding: 4rem 0 2rem;
            color: var(--gray);
        }

        footer h5, footer h6 { color: var(--dark); font-weight: 700; }

        .social-icon {
            font-size: 1.6rem;
            color: var(--gray);
            transition: all 0.2s;
        }

        .social-icon:hover {
            color: var(--primary);
            transform: translateY(-3px);
        }
    </style>
</head>
<body>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg">
    <div class="container">
        <a class="navbar-brand" href="home">Job Description Portal</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto align-items-center">
                <li class="nav-item"><a class="nav-link" href="home">Home</a></li>
                <li class="nav-item"><a class="nav-link" href="viewalljobs">All Jobs</a></li>
                <li class="nav-item"><a class="nav-link" href="addjob">Post Job</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Contact</a></li>
            </ul>
        </div>
    </div>
</nav>

<%
    JobPost myJobPost = (JobPost) request.getAttribute("jobPost");
%>

<!-- Success / Details Section -->
<section class="success-section">
    <div class="success-card">
        <div class="success-header">
            <i class="fas fa-check-circle success-icon"></i>
            <h2 class="success-title">Job Posted Successfully!</h2>
            <p class="lead" style="opacity: 0.95;">Your position is now live and visible to candidates.</p>
        </div>

        <div class="card-body">
            <%
                if (myJobPost != null) {
            %>
                <h3 class="job-title"><%= myJobPost.getPostProfile() %></h3>

                <div class="job-meta">
                    <i class="far fa-clock me-2"></i>
                    Experience Required: <strong><%= myJobPost.getReqExperience() %> year<%= myJobPost.getReqExperience() != 1 ? "s" : "" %></strong>
                </div>

                <p class="text-secondary mb-4">
                    <%= myJobPost.getPostDesc() %>
                </p>

                <div class="mt-4">
                    <strong class="d-block mb-3 text-dark">Tech Stack:</strong>
                    <div class="d-flex flex-wrap">
                        <% for (String tech : myJobPost.getPostTechStack()) { %>
                            <span class="tech-badge"><%= tech %></span>
                        <% } %>
                    </div>
                </div>

            <% } else { %>
                <p class="text-danger">No job details available. Something went wrong.</p>
            <% } %>
        </div>

        <div class="pb-5 d-flex flex-column flex-sm-row gap-3 justify-content-center">
            <a href="viewalljobs" class="btn btn-primary-action btn-action btn-lg">View All Jobs</a>
            <a href="addjob" class="btn btn-outline-action btn-action btn-lg">Post Another Job</a>
        </div>
    </div>
</section>

<!-- Footer -->
<footer>
    <div class="container">
        <div class="row">
            <div class="col-md-4 mb-5 mb-md-0">
                <h5>Job Description Portal</h5>
                <p class="mt-3">Connecting great people with great opportunities — every day</p>
            </div>

            <div class="col-md-4 mb-5 mb-md-0">
                <h6>Quick Links</h6>
                <ul class="list-unstyled mt-3">
                    <li class="mb-2"><a href="home" class="text-reset text-decoration-none">Home</a></li>
                    <li class="mb-2"><a href="viewalljobs" class="text-reset text-decoration-none">All Jobs</a></li>
                    <li class="mb-2"><a href="addjob" class="text-reset text-decoration-none">Post a Job</a></li>
                    <li><a href="#" class="text-reset text-decoration-none">Contact</a></li>
                </ul>
            </div>

            <div class="col-md-4">
                <h6>Connect</h6>
                <div class="d-flex gap-4 mt-3">
                    <a href="#" class="social-icon"><i class="fab fa-linkedin-in"></i></a>
                    <a href="#" class="social-icon"><i class="fab fa-twitter"></i></a>
                    <a href="#" class="social-icon"><i class="fab fa-github"></i></a>
                </div>
                <p class="mt-4 small">Made by Ben Gregory John</p>
            </div>
        </div>

        <div class="text-center mt-5 pt-4 border-top">
            <small>© Job Description Portal — All rights reserved</small>
        </div>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Navbar scroll effect
    window.addEventListener('scroll', () => {
        document.querySelector('.navbar').classList.toggle('scrolled', window.scrollY > 20);
    });
</script>
</body>
</html>