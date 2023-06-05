% Composite Simpson's 1/3 rule for numerical integration
% Input: f = function to integrate
%        a, b = integration limits
%        n = number of subintervals (must be even)
% Output: I = numerical approximation of integral of f from a to b
function I = CompositeSimpOneThird(a,b)
format long
f = @(x) sin(1./(x-1));
##a = 0;
##b = .999;
n = 100;
if mod(n, 2) ~= 0   % check if n is even
    error('Number of subintervals must be even')
end

h = (b - a) / n;   % width of subintervals
x = a:h:b;         % subinterval endpoints
y = f(x);          % function values at subinterval endpoints
#y = AddNoise(f,a,b,n);
I = (h / 3) * (y(1) + 4*sum(y(2:2:n)) + 2*sum(y(3:2:n-1)) + y(n+1));  % composite Simpson's 1/3 rule without noise
#I = (h / 3) * (y(1) + 4*sum(y(2:2:n)) + 2*sum(y(3:2:n-1)) + y(n)); # composite simpsons 1/3 with noise


n;
rel_err = 0;
##printf("n                 integral value      relative error\n")
##printf("%-10d        %.10f        %e\n",n,integral_val,rel_err)
##for i=1:6
##n=n*10;
##new_integral_val = composite_simp_one_third(a,b,n);
##rel_err = abs((integral_val-new_integral_val)/new_integral_val)*100;
##printf("%-10d        %.10f        %e\n",n,new_integral_val,rel_err)
##integral_val=new_integral_val;
##endfor
#printf("Integral Value: %f ",integral_val)
endfunction
