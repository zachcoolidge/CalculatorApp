% Composite Simpson's 1/3 rule for numerical integration
% Input: f = function to integrate
%        a, b = integration limits
%        n = number of subintervals (must be even)
% Output: I = numerical approximation of integral of f from a to b
function CompSimpOneThird
format long
f = @(x) sin(1./(x-1));
a = 0;
b = .999;
n = 100;
function I = composite_simp_one_third(a,b,n);
if mod(n, 2) ~= 0   % check if n is even
    error('Number of subintervals must be even')
end

h = (b - a) / n;   % width of subintervals
x = a:h:b;         % subinterval endpoints
y = f(x);          % function values at subinterval endpoints
#y = AddNoise(f,a,b,n);
I = (h / 3) * (y(1) + 4*sum(y(2:2:n)) + 2*sum(y(3:2:n-1)) + y(n+1));  % composite Simpson's 1/3 rule without noise
#I = (h / 3) * (y(1) + 4*sum(y(2:2:n)) + 2*sum(y(3:2:n-1)) + y(n)); # composite simpsons 1/3 with noise
endfunction

function fnoise2 = AddNoise(f, a, b, N)
    x = linspace(a, b, N);  % the [a, b] interval for integration
    delta = 0.02;
    fnoise2 = f(x) .* (1 + delta * (0.5 - rand(1, N)));
    error_in_function = norm(fnoise2 - f(x));
end

n;
rel_err = 0
integral_val = composite_simp_one_third(a,b,n);
printf("n                 integral value      relative error\n")
printf("%-10d        %.10f        %e\n",n,integral_val,rel_err)
for i=1:6
n=n*10;
new_integral_val = composite_simp_one_third(a,b,n);
rel_err = abs((integral_val-new_integral_val)/new_integral_val)*100;
printf("%-10d        %.10f        %e\n",n,new_integral_val,rel_err)
integral_val=new_integral_val;
endfor

endfunction

CompSimpOneThird
