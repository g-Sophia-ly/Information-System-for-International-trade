#  国际贸易有限公司业务管理信息系统  
*Design and Implementation of Business Management Information System for HW International Trade Co., Ltd.*  

---

## 项目简介 | Introduction
旨在为 **HW 国际贸易有限公司** 构建一套定制化的业务管理信息系统，解决传统管理模式下 **信息不对称、流程繁琐、效率低下** 的问题。  

This project, designs and implements a **business management information system for HW International Trade Co., Ltd.**, addressing challenges of **information asymmetry, complex processes, and low efficiency** in traditional management.  

---

## 技术栈 | Tech Stack
- **后端 | Backend**: Spring Boot, MyBatis  
- **前端 | Frontend**: Vue.js, Element-UI, ECharts  
- **数据库 | Database**: MySQL 5.7  
- **开发工具 | Tools**: IntelliJ IDEA, Navicat Premium, Npm  

---

## 系统功能 | System Features
系统共分为 **四大核心模块**，并通过子模块支撑业务全流程：  

The system is divided into **four core modules**, covering the entire business workflow:  

1. **用户管理模块 | User Management**  
   - 登录注册、角色管理、用户信息维护  
   - Login/registration, role management, user profile maintenance  

2. **业务信息管理模块 | Business Information Management**  
   - 客户信息、商品信息、供应商管理  
   - Customer, product, and supplier information management  

3. **业务流程管理模块（核心） | Business Process Management (Core)**  
   - 订单管理、配码信息、装箱资料、订船资料、报关资料  
   - Order handling, size allocation, packing list, shipping booking, customs declaration  

4. **其他功能模块 | Other Functional Modules**  
   - 工厂生产问题反馈、数据统计与可视化、日志记录  
   - Factory feedback, data visualization, log management  

---

## 系统价值 | System Value
- **降低人力成本 | Reduce manpower cost**  
  自动化业务流程，减少重复性工作。  

- **提高工作效率 | Improve efficiency**  
  优化数据流转，提升订单与客户管理速度。  

- **保障数据安全 | Data security**  
  通过数据库权限与日志机制，提升可靠性。  

- **辅助管理决策 | Decision-making support**  
  可视化统计报表，为管理层提供决策依据。  

---

## 快速开始 | Getting Started
```bash
# 克隆仓库 | Clone the repo
git clone https://github.com/<your-repo-url>.git
cd hw-trade-mis

# 启动后端 | Start backend (Spring Boot)
mvn spring-boot:run

# 启动前端 | Start frontend (Vue.js)
npm install
npm run serve
