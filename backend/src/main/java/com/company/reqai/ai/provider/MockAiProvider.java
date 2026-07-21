package com.company.reqai.ai.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

import com.company.reqai.ai.dto.AiAnalysisRequest;
import com.company.reqai.ai.dto.AiAnalysisResponse;
import com.company.reqai.ai.dto.RequirementDto;
import com.company.reqai.ai.dto.TaskDto;
import com.company.reqai.ai.dto.TestScenarioDto;

@Service
public class MockAiProvider implements AiProvider {

    @Override
    public AiAnalysisResponse analyze(AiAnalysisRequest request) {
        String content = request.getContent();

        if (content == null || content.isBlank()) {
            return new AiAnalysisResponse(List.of());
        }

        String lowerContent = content.toLowerCase(Locale.ROOT);
        List<RequirementDto> requirements = new ArrayList<>();

        if (containsAny(
                lowerContent,
                "user authentication",
                "authentication",
                "login",
                "log in",
                "sign in",
                "register",
                "registration",
                "create an account",
                "password reset",
                "forgotten password",
                "verify their email",
                "email verification"
        )) {
            requirements.add(createAuthenticationRequirement());
        }

        if (containsAny(
                lowerContent,
                "product catalog",
                "product catalogue",
                "product",
                "monthly price",
                "campaign",
                "category filter",
                "search products"
        )) {
            requirements.add(createProductCatalogRequirement());
        }

        if (containsAny(
                lowerContent,
                "shopping cart",
                "cart",
                "basket",
                "quantity",
                "clear the entire cart"
        )) {
            requirements.add(createShoppingCartRequirement());
        }

        if (containsAny(
                lowerContent,
                "order management",
                "place an order",
                "order confirmation",
                "previous orders",
                "order status"
        )) {
            requirements.add(createOrderManagementRequirement());
        }

        if (containsAny(
                lowerContent,
                "payment",
                "credit card",
                "debit card",
                "payment fails",
                "payment information"
        )) {
            requirements.add(createPaymentRequirement());
        }

        if (containsAny(
                lowerContent,
                "notifications",
                "email notifications",
                "new account is created",
                "order is placed",
                "payment is completed",
                "password is reset"
        )) {
            requirements.add(createNotificationRequirement());
        }

        if (containsAny(
                lowerContent,
                "customer profile",
                "personal information",
                "change their password",
                "communication preferences"
        )) {
            requirements.add(createCustomerProfileRequirement());
        }

        if (containsAny(
                lowerContent,
                "general expectations",
                "responsive user interface",
                "input validation",
                "meaningful error messages",
                "audit purposes",
                "operations should be logged"
        )) {
            requirements.add(createGeneralQualityRequirement());
        }

        if (requirements.isEmpty()) {
            requirements.add(createGenericRequirement(content));
        }

        return new AiAnalysisResponse(requirements);
    }

    private boolean containsAny(String content, String... keywords) {
        for (String keyword : keywords) {
            if (content.contains(keyword)) {
                return true;
            }
        }

        return false;
    }

    private RequirementDto createAuthenticationRequirement() {
        return new RequirementDto(
                "User Authentication",
                "Customers should securely register, verify their email, log in and reset forgotten passwords.",
                "HIGH",
                "HIGH",
                List.of(
                        new TaskDto(
                                "Develop Login API",
                                "Create an endpoint that authenticates customers using email and password.",
                                "NEW",
                                List.of(
                                        new TestScenarioDto(
                                                "Successful Login",
                                                "A customer with valid credentials should access the portal."
                                        ),
                                        new TestScenarioDto(
                                                "Invalid Credentials",
                                                "The system should reject invalid credentials and display a meaningful error."
                                        )
                                )
                        ),
                        new TaskDto(
                                "Develop Customer Registration",
                                "Create the registration workflow using customer personal information.",
                                "NEW",
                                List.of(
                                        new TestScenarioDto(
                                                "Successful Registration",
                                                "A new account should be created when valid information is submitted."
                                        ),
                                        new TestScenarioDto(
                                                "Duplicate Email",
                                                "The system should reject registration with an existing email address."
                                        )
                                )
                        ),
                        new TaskDto(
                                "Implement Email Verification",
                                "Generate and validate an email verification link before portal access is granted.",
                                "NEW",
                                List.of(
                                        new TestScenarioDto(
                                                "Valid Verification Link",
                                                "The customer account should be verified with a valid link."
                                        ),
                                        new TestScenarioDto(
                                                "Invalid Verification Link",
                                                "An invalid or expired verification link should be rejected."
                                        )
                                )
                        ),
                        new TaskDto(
                                "Implement Password Reset",
                                "Allow customers to request and use a secure password reset link.",
                                "NEW",
                                List.of(
                                        new TestScenarioDto(
                                                "Password Reset Request",
                                                "A reset link should be generated for a registered email address."
                                        ),
                                        new TestScenarioDto(
                                                "Expired Reset Link",
                                                "An expired password reset link should not change the password."
                                        )
                                )
                        )
                )
        );
    }

    private RequirementDto createProductCatalogRequirement() {
        return new RequirementDto(
                "Product Catalog",
                "Customers should browse available products and view product pricing and campaign information.",
                "MEDIUM",
                "MEDIUM",
                List.of(
                        new TaskDto(
                                "Develop Product Listing API",
                                "Create an endpoint that returns available products and their basic information.",
                                "NEW",
                                List.of(
                                        new TestScenarioDto(
                                                "List Available Products",
                                                "The API should return all available products."
                                        ),
                                        new TestScenarioDto(
                                                "Display Product Details",
                                                "Each product should include its name and description."
                                        )
                                )
                        ),
                        new TaskDto(
                                "Implement Product Search and Filtering",
                                "Allow customers to search products by name and filter them by category.",
                                "NEW",
                                List.of(
                                        new TestScenarioDto(
                                                "Search Product by Name",
                                                "Only products matching the search text should be returned."
                                        ),
                                        new TestScenarioDto(
                                                "Filter Products by Category",
                                                "Only products from the selected category should be returned."
                                        )
                                )
                        ),
                        new TaskDto(
                                "Display Pricing and Campaigns",
                                "Display each product's monthly price and available campaigns.",
                                "NEW",
                                List.of(
                                        new TestScenarioDto(
                                                "Display Monthly Price",
                                                "The correct monthly price should be displayed for each product."
                                        ),
                                        new TestScenarioDto(
                                                "Display Available Campaigns",
                                                "Active campaigns should be displayed with the related product."
                                        )
                                )
                        )
                )
        );
    }

    private RequirementDto createShoppingCartRequirement() {
        return new RequirementDto(
                "Shopping Cart",
                "Customers should manage products in a shopping cart before placing an order.",
                "HIGH",
                "MEDIUM",
                List.of(
                        new TaskDto(
                                "Add Products to Cart",
                                "Create an operation that adds a selected product to the customer's cart.",
                                "NEW",
                                List.of(
                                        new TestScenarioDto(
                                                "Add Valid Product",
                                                "The selected product should be added to the shopping cart."
                                        ),
                                        new TestScenarioDto(
                                                "Add Existing Product",
                                                "Adding an existing product should update its quantity correctly."
                                        )
                                )
                        ),
                        new TaskDto(
                                "Update Product Quantities",
                                "Allow customers to change product quantities in the cart.",
                                "NEW",
                                List.of(
                                        new TestScenarioDto(
                                                "Increase Quantity",
                                                "The product quantity and total price should increase correctly."
                                        ),
                                        new TestScenarioDto(
                                                "Invalid Quantity",
                                                "Zero or negative quantities should be rejected."
                                        )
                                )
                        ),
                        new TaskDto(
                                "Remove and Clear Cart Items",
                                "Allow customers to remove one product or clear the entire cart.",
                                "NEW",
                                List.of(
                                        new TestScenarioDto(
                                                "Remove Product",
                                                "The selected product should be removed from the cart."
                                        ),
                                        new TestScenarioDto(
                                                "Clear Cart",
                                                "All products should be removed from the shopping cart."
                                        )
                                )
                        ),
                        new TaskDto(
                                "Calculate Cart Total",
                                "Calculate the total price using product prices and quantities.",
                                "NEW",
                                List.of(
                                        new TestScenarioDto(
                                                "Calculate Multiple Items",
                                                "The total should equal the sum of all cart item subtotals."
                                        ),
                                        new TestScenarioDto(
                                                "Empty Cart Total",
                                                "The total price of an empty cart should be zero."
                                        )
                                )
                        )
                )
        );
    }

    private RequirementDto createOrderManagementRequirement() {
        return new RequirementDto(
                "Order Management",
                "Customers should create orders from their carts and view previous orders and statuses.",
                "HIGH",
                "HIGH",
                List.of(
                        new TaskDto(
                                "Create Order from Cart",
                                "Convert the current shopping cart into an order.",
                                "NEW",
                                List.of(
                                        new TestScenarioDto(
                                                "Create Order Successfully",
                                                "An order should be created from valid cart items."
                                        ),
                                        new TestScenarioDto(
                                                "Prevent Empty Order",
                                                "The system should reject order creation when the cart is empty."
                                        )
                                )
                        ),
                        new TaskDto(
                                "Display Order Confirmation",
                                "Display confirmation information after an order is created.",
                                "NEW",
                                List.of(
                                        new TestScenarioDto(
                                                "Show Confirmation",
                                                "The customer should see the created order number and confirmation status."
                                        )
                                )
                        ),
                        new TaskDto(
                                "Display Order History",
                                "Return previous orders together with their current statuses.",
                                "NEW",
                                List.of(
                                        new TestScenarioDto(
                                                "List Previous Orders",
                                                "The customer should see all orders associated with their account."
                                        ),
                                        new TestScenarioDto(
                                                "Display Current Status",
                                                "Each order should display its current status."
                                        )
                                )
                        )
                )
        );
    }

    private RequirementDto createPaymentRequirement() {
        return new RequirementDto(
                "Payment",
                "Customers should pay for orders using credit or debit cards with proper validation and error handling.",
                "HIGH",
                "HIGH",
                List.of(
                        new TaskDto(
                                "Validate Payment Information",
                                "Validate required card and payment fields before processing.",
                                "NEW",
                                List.of(
                                        new TestScenarioDto(
                                                "Valid Payment Information",
                                                "Valid card details should pass payment validation."
                                        ),
                                        new TestScenarioDto(
                                                "Invalid Payment Information",
                                                "Invalid or incomplete card details should be rejected."
                                        )
                                )
                        ),
                        new TaskDto(
                                "Process Credit and Debit Card Payments",
                                "Create a payment service that supports credit and debit cards.",
                                "NEW",
                                List.of(
                                        new TestScenarioDto(
                                                "Successful Credit Card Payment",
                                                "A valid credit card payment should be completed successfully."
                                        ),
                                        new TestScenarioDto(
                                                "Successful Debit Card Payment",
                                                "A valid debit card payment should be completed successfully."
                                        )
                                )
                        ),
                        new TaskDto(
                                "Handle Payment Failures",
                                "Return meaningful error information when payment cannot be completed.",
                                "NEW",
                                List.of(
                                        new TestScenarioDto(
                                                "Declined Payment",
                                                "The customer should receive a clear declined-payment message."
                                        ),
                                        new TestScenarioDto(
                                                "Payment Service Failure",
                                                "A temporary payment service error should not create a successful payment."
                                        )
                                )
                        )
                )
        );
    }

    private RequirementDto createNotificationRequirement() {
        return new RequirementDto(
                "Notifications",
                "Customers should receive email notifications for important account, order and payment events.",
                "MEDIUM",
                "MEDIUM",
                List.of(
                        new TaskDto(
                                "Send Account Emails",
                                "Send emails when an account is created and when a password reset is requested.",
                                "NEW",
                                List.of(
                                        new TestScenarioDto(
                                                "Account Created Email",
                                                "A confirmation email should be sent after account creation."
                                        ),
                                        new TestScenarioDto(
                                                "Password Reset Email",
                                                "A password reset email should be sent after a valid reset request."
                                        )
                                )
                        ),
                        new TaskDto(
                                "Send Order and Payment Emails",
                                "Send notification emails after order placement and successful payment.",
                                "NEW",
                                List.of(
                                        new TestScenarioDto(
                                                "Order Placed Email",
                                                "An order confirmation email should be sent after an order is placed."
                                        ),
                                        new TestScenarioDto(
                                                "Payment Completed Email",
                                                "A payment confirmation email should be sent after successful payment."
                                        )
                                )
                        )
                )
        );
    }

    private RequirementDto createCustomerProfileRequirement() {
        return new RequirementDto(
                "Customer Profile",
                "Customers should maintain their personal information, password and communication preferences.",
                "MEDIUM",
                "MEDIUM",
                List.of(
                        new TaskDto(
                                "Update Personal Information",
                                "Allow customers to view and update their personal information.",
                                "NEW",
                                List.of(
                                        new TestScenarioDto(
                                                "Update Valid Information",
                                                "Valid personal information should be saved successfully."
                                        ),
                                        new TestScenarioDto(
                                                "Reject Invalid Information",
                                                "Invalid profile information should not be saved."
                                        )
                                )
                        ),
                        new TaskDto(
                                "Change Account Password",
                                "Allow authenticated customers to change their current password.",
                                "NEW",
                                List.of(
                                        new TestScenarioDto(
                                                "Change Password Successfully",
                                                "The password should be changed when the current password is correct."
                                        ),
                                        new TestScenarioDto(
                                                "Incorrect Current Password",
                                                "The password change should be rejected when the current password is incorrect."
                                        )
                                )
                        ),
                        new TaskDto(
                                "Manage Communication Preferences",
                                "Allow customers to enable or disable supported communications.",
                                "NEW",
                                List.of(
                                        new TestScenarioDto(
                                                "Enable Communication",
                                                "The selected communication preference should be enabled."
                                        ),
                                        new TestScenarioDto(
                                                "Disable Communication",
                                                "The selected communication preference should be disabled."
                                        )
                                )
                        )
                )
        );
    }

    private RequirementDto createGeneralQualityRequirement() {
        return new RequirementDto(
                "General Quality and Audit",
                "The portal should be responsive, validate input, display meaningful errors and log operations for audit purposes.",
                "HIGH",
                "HIGH",
                List.of(
                        new TaskDto(
                                "Implement Responsive User Interface",
                                "Ensure the portal works correctly on desktop, tablet and mobile screens.",
                                "NEW",
                                List.of(
                                        new TestScenarioDto(
                                                "Desktop Layout",
                                                "The portal should display correctly on a desktop screen."
                                        ),
                                        new TestScenarioDto(
                                                "Mobile Layout",
                                                "The portal should remain usable on a mobile screen."
                                        )
                                )
                        ),
                        new TaskDto(
                                "Implement Validation and Error Handling",
                                "Validate requests and return consistent, meaningful error messages.",
                                "NEW",
                                List.of(
                                        new TestScenarioDto(
                                                "Reject Invalid Input",
                                                "Invalid input should be rejected before the operation is completed."
                                        ),
                                        new TestScenarioDto(
                                                "Display Meaningful Error",
                                                "The customer should receive a clear and non-technical error message."
                                        )
                                )
                        ),
                        new TaskDto(
                                "Implement Audit Logging",
                                "Record important customer and system operations for audit purposes.",
                                "NEW",
                                List.of(
                                        new TestScenarioDto(
                                                "Log Successful Operation",
                                                "A successful operation should create an audit record."
                                        ),
                                        new TestScenarioDto(
                                                "Log Failed Operation",
                                                "A failed operation should create an audit record without exposing sensitive data."
                                        )
                                )
                        )
                )
        );
    }

    private RequirementDto createGenericRequirement(String content) {
        String summary = content.length() > 120
                ? content.substring(0, 120) + "..."
                : content;

        return new RequirementDto(
                "General Requirement Analysis",
                "No predefined category was detected. Manual review may be required. Summary: " + summary,
                "MEDIUM",
                "MEDIUM",
                List.of(
                        new TaskDto(
                                "Review Customer Requirement",
                                "Review the uploaded text and identify development-ready tasks.",
                                "NEW",
                                List.of(
                                        new TestScenarioDto(
                                                "Complete Manual Review",
                                                "An analyst should review and clarify the requirement."
                                        )
                                )
                        )
                )
        );
    }
}